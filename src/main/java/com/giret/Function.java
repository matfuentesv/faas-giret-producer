package com.giret;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridEvent;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.azure.messaging.eventgrid.EventGridPublisherClientBuilder;
import com.giret.model.Loan;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;
import java.util.Optional;
import com.google.gson.JsonObject;

public class Function {

    private final String eventGridTopicEndpoint = "https://giret-event-grid.eastus2-1.eventgrid.azure.net/api/events";
    private final String eventGridTopicKey = "Fr5wIgn4HUcBblvhMD34lg37GLHWVuY1YjkVK9lVACD35ZgyZqU4JQQJ99BGACHYHv6XJ3w3AAABAZEGd6hz";

    private final EventGridPublisherClient<EventGridEvent> client = new EventGridPublisherClientBuilder()
            .endpoint(eventGridTopicEndpoint)
            .credential(new AzureKeyCredential(eventGridTopicKey))
            .buildEventGridEventPublisherClient();

    @FunctionName("createLoan")
    public HttpResponseMessage createLoan(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}) HttpRequestMessage<Optional<Loan>> request,
            final ExecutionContext context) {

        if (!request.getBody().isPresent()) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Body es requerido")
                    .build();
        }

        Loan loan = request.getBody().get();

        // Construir payload plano y limpio (solo campos relevantes)
        JsonObject data = new JsonObject();
        data.addProperty("prestamoId", loan.getIdPrestamo());
        data.addProperty("recursoId", loan.getRecursoId());

        // Crear evento Event Grid bien formado
        EventGridEvent event = new EventGridEvent(
                "Prestamo",                // subject
                "Prestamo.CREADO",         // eventType
                BinaryData.fromObject(data), // data como JSON
                "1.0"                      // dataVersion
        );

        client.sendEvent(event);
        context.getLogger().info("Evento Prestamo.CREADO publicado: " + data.toString());

        return request.createResponseBuilder(HttpStatus.CREATED)
                .body("Prestamo creado y evento publicado correctamente")
                .build();
    }
}

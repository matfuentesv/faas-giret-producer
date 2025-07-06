package com.giret;


import java.util.Optional;
import java.util.logging.Logger;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.util.BinaryData;
import com.azure.messaging.eventgrid.EventGridEvent;
import com.azure.messaging.eventgrid.EventGridPublisherClient;
import com.azure.messaging.eventgrid.EventGridPublisherClientBuilder;
import com.giret.model.Loan;
import com.giret.model.Resource;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;


public class Function {

    private final String eventGridTopicEndpoint = "https://giret-event-grid.eastus2-1.eventgrid.azure.net/api/events";
    private final String eventGridTopicKey = "Fr5wIgn4HUcBblvhMD34lg37GLHWVuY1YjkVK9lVACD35ZgyZqU4JQQJ99BGACHYHv6XJ3w3AAABAZEGd6hz";

    private final EventGridPublisherClient<EventGridEvent> client = new EventGridPublisherClientBuilder()
            .endpoint(eventGridTopicEndpoint)
            .credential(new AzureKeyCredential(eventGridTopicKey))
            .buildEventGridEventPublisherClient();



    @FunctionName("updateResource")
    public HttpResponseMessage updateResource(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST})
            HttpRequestMessage<Optional<Resource>> request,
            final ExecutionContext context) {

        Logger logger = context.getLogger();
        logger.info("🚀 Ejecutando Function 'createRecurso'...");

        if (!request.getBody().isPresent()) {
            logger.warning("Body vacío");
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Body es requerido")
                    .build();
        }

        try {
            Resource nuevoRecurso = request.getBody().get();
            logger.info("📦 Payload recibido: " + nuevoRecurso);

            EventGridEvent event = new EventGridEvent(
                    "Recurso",
                    "Recurso.PRESTADO",
                    BinaryData.fromObject(nuevoRecurso),
                    "1.0"
            );

            client.sendEvent(event);
            logger.info("✅ Evento 'Recurso.PRESTADO' publicado a Event Grid");

            return request.createResponseBuilder(HttpStatus.CREATED)
                    .body("Recurso creado y evento publicado correctamente")
                    .build();

        } catch (Exception e) {
            logger.severe("💥 Error en updateResource: " + e.getMessage());
            e.printStackTrace();
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage())
                    .build();
        }
    }



    @FunctionName("createPrestamo")
    public HttpResponseMessage createPrestamo(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST})
            HttpRequestMessage<Optional<Loan>> request,
            final ExecutionContext context) {

        Logger logger = context.getLogger();
        logger.info("🚀 Ejecutando Function 'createPrestamo'...");

        if (!request.getBody().isPresent()) {
            logger.warning("⚠️ Body vacío");
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Body es requerido")
                    .build();
        }

        try {
            Loan nuevoLoan = request.getBody().get();
            logger.info("📦 Payload recibido: " + nuevoLoan);

            EventGridEvent event = new EventGridEvent(
                    "Prestamo",
                    "Prestamo.CREADO",
                    BinaryData.fromObject(nuevoLoan),
                    "1.0"
            );

            client.sendEvent(event);
            logger.info("✅ Prestamo 'Prestamo.CREADO' publicado a Event Grid");

            return request.createResponseBuilder(HttpStatus.CREATED)
                    .body("Prestamo creado y evento publicado correctamente")
                    .build();

        } catch (Exception e) {
            logger.severe("💥 Error en createRecurso: " + e.getMessage());
            e.printStackTrace();
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage())
                    .build();
        }
    }


}

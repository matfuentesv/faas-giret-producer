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



public class Function {

    private final String eventGridTopicEndpoint = "https://user-role-events-topic.eastus2-1.eventgrid.azure.net/api/events";
    private final String eventGridTopicKey = "FWObSItpv8zDesKQBo5bEYvYKWCGZ9yq1wR82E7DmSm5LHhmGaTkJQQJ99BDACHYHv6XJ3w3AAABAZEGBFN8";

    private final EventGridPublisherClient<EventGridEvent> client = new EventGridPublisherClientBuilder()
            .endpoint(eventGridTopicEndpoint)
            .credential(new AzureKeyCredential(eventGridTopicKey))
            .buildEventGridEventPublisherClient();



    @FunctionName("createPrestamo")
    public HttpResponseMessage createPrestamo(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}) HttpRequestMessage<Optional<Loan>> request,
            final ExecutionContext context) {

        if (!request.getBody().isPresent()) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Body es requerido")
                    .build();
        }

        Loan nuevoPrestamo = request.getBody().get();

        EventGridEvent event = new EventGridEvent(
                "Prestamo",
                "PrestamoCreado.",
                BinaryData.fromObject(nuevoPrestamo),
                "1.0"
        );
        client.sendEvent(event);

        return request.createResponseBuilder(HttpStatus.CREATED)
                .body("Prestamo creado y evento publicado")
                .build();
    }


}

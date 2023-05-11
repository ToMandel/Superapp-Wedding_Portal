package superapp.logic.async;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import superapp.Converter;
import superapp.boundries.MiniAppCommandBoundary;
import superapp.dal.MiniAppCommandCrud;
import superapp.dal.SupperAppObjectCrud;
import superapp.dal.UserCrud;
import superapp.data.MiniAppCommandEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;


@Component
public class AsyncHandler {

    private ObjectMapper jackson;
    private Converter converter;
    private MiniAppCommandCrud miniAppCommandCrud;
    private SupperAppObjectCrud supperAppObjectCrud;
    private UserCrud userCrud;

    @Autowired
    public AsyncHandler(Converter converter, MiniAppCommandCrud miniAppCommandCrud, SupperAppObjectCrud supperAppObjectCrud, UserCrud userCrud){
        this.jackson = new ObjectMapper();
        this.converter = new Converter();
        this.miniAppCommandCrud = miniAppCommandCrud;
        this.supperAppObjectCrud = supperAppObjectCrud;
        this.userCrud = userCrud;
    }

    @JmsListener(destination = "asyncMiniAppQueue")
    public void handleMiniAppCommandFromQueue(String json){
        System.err.println("**** " + json);
        try{
            MiniAppCommandBoundary boundary = this.jackson.readValue(json, MiniAppCommandBoundary.class);
            if (boundary.getCommandAttributes() == null)
                boundary.setCommandAttributes(new HashMap<>());
            boundary.getCommandAttributes().put("status", "remote-is-done");

            MiniAppCommandEntity entity = this.converter.miniAppCommandToEntity(boundary);
            if (entity.getCommandId() == null)
                entity.setCommandId(UUID.randomUUID().toString());
            if (entity.getInvocationTimestamp() == null)
                entity.setInvocationTimestamp(new Date());

            entity = this.miniAppCommandCrud.save(entity);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

    }

}

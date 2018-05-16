package nl.oscar.kwetter.service.websocket;

import nl.oscar.kwetter.domain.Kwetter;
import nl.oscar.kwetter.service.kwetter.KwetterListener;
import nl.oscar.kwetter.service.kwetter.KwetterService;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/socket/{id}", encoders = KwetterJsonEncoder.class, configurator = HttpSessionProvider.class)
public class WebSocketKwetter implements KwetterListener {

    @Inject
    private KwetterService service;

    private Session session;
    private String id;

    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        this.session = session;
        this.id = id;

        service.addListener(this);
    }

    @OnClose
    public void onClose(@PathParam("id") String id) {
        this.session = null;
        this.id = id;

        service.removeListener(this);
    }

    @Override
    public void onKwetter(Kwetter kwetter) {
        session.getAsyncRemote().sendObject(kwetter);
    }
}

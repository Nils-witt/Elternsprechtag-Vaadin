package de.nilswitt.vaadin.views.helloworld;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import de.nilswitt.vaadin.data.entities.User;
import de.nilswitt.vaadin.security.AuthenticatedUser;
import de.nilswitt.vaadin.views.MainLayout;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@PageTitle("Hello World")
@Route(value = "hello", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@AnonymousAllowed
public class HelloWorldView extends HorizontalLayout {

    private final TextField name;
    private final Button sayHello;

    public HelloWorldView(JavaMailSender emailSender, AuthenticatedUser authenticatedUser) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@baeldung.com");

        message.setSubject("subject");

        if (authenticatedUser.get().isPresent()) {
            User user = authenticatedUser.get().get();
            message.setTo(user.getUsername());
            message.setText(user.getName());
        }





        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            emailSender.send(message);
        });
        sayHello.addClickShortcut(Key.ENTER);

        setMargin(true);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);

        add(name, sayHello);


    }

}

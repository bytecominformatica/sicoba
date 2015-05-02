package net.servehttp.bytecom;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class GreeterTest {

  @Deployment
  public static WebArchive createDeployment() {
    WebArchive war =
        ShrinkWrap.create(WebArchive.class).addClass(Greeter.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    System.out.println(war.toString(true));
    return war;
  }

  @Inject
  Greeter greeter;

  @Test
  public void should_create_greeting() {
    Assert.assertEquals("Hello, Earthling!", greeter.createGreeting("Earthling"));
    greeter.greet(System.out, "Earthling");
  }
}

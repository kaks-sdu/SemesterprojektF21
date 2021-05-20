package io.github.arkobat.kolorkarl.core;

import org.apache.felix.framework.FrameworkFactory;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.ServiceLoader;

public class IntegrationTest {

    @Test
    public void integrationTest() throws BundleException {
        FrameworkFactory ffFactory = new FrameworkFactory();
        Framework ff = ffFactory.newFramework(new HashMap<String, String>());
        ff.start();

        System.out.println("Bundles pre installed:");
        for(Bundle bundle : ff.getBundleContext().getBundles()){
            System.out.println(bundle.getSymbolicName());
        }
        System.out.println("===============================");

        try
        {
            // Bundle 0 is the org.apache.felix.framework bundle
            final Bundle b = ff.getBundleContext ().getBundle (0);

            assertNotNull (b);
            assertEquals (Bundle.ACTIVE, b.getState ());

            // No leading slash is important as the ClassLoader is used!
            assertNotNull (b.getResource ("org/apache/felix/framework/util/Util.class"));
            // Assert not throws bundle exception. If this is thrown, the bundle cannot be found.
            String jarUrl = Paths.get(new File("").getAbsolutePath()).toString();
            jarUrl = jarUrl.replace("Core", "runner\\bundles\\SemesterProjectGroup1_Player_1.0.0.SNAPSHOT.jar").replace('\\', '/');
            System.out.println("Jar URL:");
            System.out.println("file:///" + jarUrl);

            Bundle playerBundle = ff.getBundleContext().installBundle("file://" + jarUrl);
            System.out.println("Player bundle: " + playerBundle.getSymbolicName());
            //assertNotNull(b.getResource("reference:file:../runner/bundles/SemesterProjectGroup1_Player_1.0.0.SNAPSHOT.jar"));
        }
        finally
        {
            ff.stop ();
            System.out.println("Test done!");
        }
    }
}

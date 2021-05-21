package io.github.arkobat.kolorkarl.core;

import org.apache.felix.framework.FrameworkFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.launch.Framework;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;


public class IntegrationTest {

    static BundleContext context;

    @BeforeClass
    public static void setup() {
        // Start the framework
        FrameworkFactory frameworkFactory = new FrameworkFactory();

        Map<String, String> config = new HashMap<String, String>();
        config.put("osgi.console", "");
        config.put("osgi.clean", "true");
        config.put("osgi.noShutdown", "true");
        config.put("eclipse.ignoreApp", "true");
        config.put("osgi.bundles.defaultStartLevel", "4");
        config.put("osgi.configuration.area", "./configuration");

        // automated bundles deployment
        config.put("felix.fileinstall.dir", "./dropins");
        config.put("felix.fileinstall.noInitialDelay", "true");
        config.put("felix.fileinstall.start.level", "4");

        Framework framework = frameworkFactory.newFramework(config);

        try {
            framework.start();
        } catch (BundleException e) {
            e.printStackTrace();
        }

        context = framework.getBundleContext();
    }

    @Test
    public void installBundle_AssetManagerInstalled_newFramework() {
        try {
            installBundle("SemesterProjectGroup1_AssetManager_1.0.0.SNAPSHOT.jar");
        } catch (BundleException e) {
            fail("Could not install AssetManager bundle");
        }
    }

    @Test
    public void installBundle_unknownBundle_newFramework() {
        assertThrows(BundleException.class, () -> installBundle("unknown_bundle.jar"));
    }

    public void installBundle(String jar) throws BundleException {
        String jarUrl = Paths.get(new File("").getAbsolutePath(), "runner", "bundles", jar).toString();
        jarUrl = jarUrl.replace("Core", "");

        context.installBundle("file:" + jarUrl);
    }
}

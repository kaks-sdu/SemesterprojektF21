package io.github.arkobat.semesterprojektF21.listener;

import io.github.arkobat.semesterprojektF21.assetmanager.AssetLoader;
import io.github.arkobat.semesterprojektF21.common.Color;
import io.github.arkobat.semesterprojektF21.common.Location;
import io.github.arkobat.semesterprojektF21.common.entity.Entity;
import io.github.arkobat.semesterprojektF21.common.event.EventListener;
import io.github.arkobat.semesterprojektF21.common.event.WorldStartEvent;
import io.github.arkobat.semesterprojektF21.enemy.Enemy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

public class WorldStartListener extends EventListener {

    @Override
    public void onWorldStart(WorldStartEvent event) {
        Collection<Entity> enemies = event.getWorld().getEntities(Enemy.class);
        for (Entity enemy : enemies) {
            event.getWorld().removeEntity(enemy);
        }

        try {
            File file = AssetLoader.getInstance("Enemy").loadFile("enemy/" + event.getWorld().getName() + ".enemies").file();
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            Document doc = dbf.newDocumentBuilder().parse(file);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("enemy");
            // nodeList is not iterable, so we are using for loop
            for (int itr = 0; itr < nodeList.getLength(); itr++) {
                Node node = nodeList.item(itr);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    int spawnX = Integer.parseInt(element.getElementsByTagName("spawnX").item(0).getTextContent());
                    int spawnY = Integer.parseInt(element.getElementsByTagName("spawnY").item(0).getTextContent());
                    Color color = Color.valueOf(element.getElementsByTagName("color").item(0).getTextContent());
                    Enemy enemy = new Enemy(event.getWorld(), new Color[]{color}, new Location(spawnX * 8F, spawnY * 8F));
                    event.getWorld().addEntity(enemy);
                }
            }
        } catch (FileNotFoundException ignored) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

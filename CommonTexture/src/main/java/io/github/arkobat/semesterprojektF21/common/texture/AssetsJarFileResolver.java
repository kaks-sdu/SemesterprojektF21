package io.github.arkobat.semesterprojektF21.common.texture;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

//TODO: Move to managers folder without breaking
public class AssetsJarFileResolver implements FileHandleResolver {

    @Override
    public FileHandle resolve(String fileName) {

        return new JarFileHandleStream(fileName);
    }
}

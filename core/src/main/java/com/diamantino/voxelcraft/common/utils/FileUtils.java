package com.diamantino.voxelcraft.common.utils;

import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileUtils {
    public static final String folderName = "VoxelCraft";

    private enum OSType {
        Windows,
        Unix,
        Mac,
        Solaris,
        Unknown
    }

    public static List<FileHandle> getAllFilesInFolderInternal(FileHandle directory, String extensionFilter) {
        List<FileHandle> files = new ArrayList<>();

        FileHandle[] newHandles = directory.list();
        for (FileHandle f : newHandles)
        {
            if (f.isDirectory())
            {
                files.addAll(getAllFilesInFolderInternal(f, extensionFilter));
            }
            else if (Objects.equals(f.extension(), extensionFilter))
            {
                files.add(f);
            }
        }

        return files;
    }

    // TODO: Finish this
    public static String getVoxelCraftFolder() {
        return switch (getOsType()) {
            case Windows -> System.getenv("APPDATA") + File.separator + folderName;
            case Unix, Solaris, Unknown -> System.getProperty("user.home") + File.separator + folderName;
            case Mac -> System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator + folderName;
        };
    }

    private static OSType getOsType() {
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("win")) {
            return OSType.Windows;
        } else if (osName.contains("mac")) {
            return OSType.Mac;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            return OSType.Unix;
        } else if (osName.contains("sunos")) {
            return OSType.Solaris;
        } else {
            return OSType.Unknown;
        }
    }
}

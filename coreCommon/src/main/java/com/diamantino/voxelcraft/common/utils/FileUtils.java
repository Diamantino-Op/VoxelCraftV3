package com.diamantino.voxelcraft.common.utils;

import com.badlogic.gdx.files.FileHandle;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Utility class for file operations.
 *
 * @author Diamantino
 */
public class FileUtils {
    /**
     * The name of the folder where the game files are stored.
     */
    public static final String folderName = "VoxelCraft";

    /**
     * The operating system types.
     */
    private enum OSType {
        Windows,
        Unix,
        Mac,
        Solaris,
        Unknown
    }

    /**
     * Gets all files in an internal folder with a specific extension.
     *
     * @param directory The internal folder to search for files.
     * @param extensionFilter The extension of the files to search for.
     * @return A list of files with the specified extension.
     */
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

    /**
     * Merges multiple paths into a single path.
     *
     * @param paths The paths to merge.
     * @return The merged path.
     */
    public static String mergePaths(String... paths) {
        StringBuilder mergedPath = new StringBuilder();

        for (String path : paths) {
            mergedPath.append(path);
            mergedPath.append(File.separator);
        }

        return mergedPath.toString();
    }

    // TODO: Finish this

    /**
     * Gets the path to the VoxelCraft folder.
     * @return The path to the VoxelCraft folder.
     */
    public static String getVoxelCraftFolder() {
        return switch (getOsType()) {
            case Windows -> System.getenv("APPDATA") + File.separator + folderName;
            case Unix, Solaris, Unknown -> System.getProperty("user.home") + File.separator + folderName;
            case Mac -> System.getProperty("user.home") + File.separator + "Library" + File.separator + "Application Support" + File.separator + folderName;
        };
    }

    /**
     * Gets the operating system type.
     * @return The operating system type.
     */
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

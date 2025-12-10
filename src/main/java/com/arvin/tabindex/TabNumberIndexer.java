package com.arvin.tabindex;

import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorTabTitleProvider;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Provides numbered titles for editor tabs so Vim users can rely on numeric shortcuts.
 */
public class TabNumberIndexer implements EditorTabTitleProvider, DumbAware {

    @Override
    public @Nullable String getEditorTabTitle(@NotNull Project project, @NotNull VirtualFile file) {
        // Ensure the refresh listener is installed so tab titles stay current.
        project.getService(TabIndexProjectService.class);

        int index = resolveIndex(project, file);
        if (index < 0) {
            return null;
        }
        return index + " " + file.getPresentableName();
    }

    private static int resolveIndex(@NotNull Project project, @NotNull VirtualFile targetFile) {
        FileEditorManagerEx manager = FileEditorManagerEx.getInstanceEx(project);
        if (manager == null) {
            return -1;
        }

        EditorWindow currentWindow = manager.getCurrentWindow();
        if (currentWindow != null) {
            int index = findIndex(currentWindow, targetFile);
            if (index >= 0) {
                return index;
            }
        }

        for (EditorWindow window : manager.getWindows()) {
            int index = findIndex(window, targetFile);
            if (index >= 0) {
                return index;
            }
        }

        return -1;
    }

    private static int findIndex(@NotNull EditorWindow window, @NotNull VirtualFile targetFile) {
        VirtualFile[] files = window.getFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].equals(targetFile)) {
                return i + 1;
            }
        }
        return -1;
    }
}

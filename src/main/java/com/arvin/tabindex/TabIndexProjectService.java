package com.arvin.tabindex;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * Listens for editor tab changes and triggers a refresh so titles pull the latest indices.
 */
@Service(Service.Level.PROJECT)
public final class TabIndexProjectService {

    private final Project project;

    public TabIndexProjectService(@NotNull Project project) {
        this.project = project;
        project.getMessageBus()
                .connect(project)
                .subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileEditorManagerListener() {
                    @Override
                    public void fileOpened(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                        scheduleRefresh();
                    }

                    @Override
                    public void fileClosed(@NotNull FileEditorManager source, @NotNull VirtualFile file) {
                        scheduleRefresh();
                    }

                    @Override
                    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
                        scheduleRefresh();
                    }
                });
        scheduleRefresh();
    }

    private void scheduleRefresh() {
        ApplicationManager.getApplication().invokeLater(this::refreshTabs, project.getDisposed());
    }

    private void refreshTabs() {
        if (project.isDisposed()) {
            return;
        }
        
        FileEditorManagerEx manager = FileEditorManagerEx.getInstanceEx(project);
        if (manager == null) {
            return;
        }

        // Update all file presentations
        for (EditorWindow window : manager.getWindows()) {
            VirtualFile[] files = window.getFiles();
            for (VirtualFile file : files) {
                manager.updateFilePresentation(file);
            }
        }
        
        // Force UI repaint
        manager.getSplitters().repaint();
    }
}

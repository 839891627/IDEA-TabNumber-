package com.arvin.tabindex;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Base action for jumping to a specific tab index within the active editor window.
 */
public abstract class TabNumberIndexAction extends AnAction implements DumbAware {

    private final int tabIndex;

    protected TabNumberIndexAction(int tabIndex) {
        super("Select Tab " + tabIndex, "Activate editor tab #" + tabIndex, null);
        this.tabIndex = tabIndex;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        FileEditorManagerEx manager = FileEditorManagerEx.getInstanceEx(project);
        if (manager == null) {
            return;
        }

        EditorWindow targetWindow = pickWindow(manager);
        if (targetWindow == null) {
            return;
        }

        VirtualFile targetFile = resolveFile(targetWindow);
        if (targetFile == null) {
            return;
        }

        manager.openFile(targetFile, true);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        Project project = e.getProject();
        if (project == null) {
            presentation.setEnabled(false);
            return;
        }

        FileEditorManagerEx manager = FileEditorManagerEx.getInstanceEx(project);
        if (manager == null) {
            presentation.setEnabled(false);
            return;
        }

        EditorWindow window = pickWindow(manager);
        if (window == null) {
            presentation.setEnabled(false);
            return;
        }

        presentation.setEnabled(window.getFiles().length >= tabIndex);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

    private @Nullable VirtualFile resolveFile(@NotNull EditorWindow window) {
        VirtualFile[] files = window.getFiles();
        int zeroBasedIndex = tabIndex - 1;
        if (zeroBasedIndex < 0 || zeroBasedIndex >= files.length) {
            return null;
        }
        return files[zeroBasedIndex];
    }

    private static @Nullable EditorWindow pickWindow(@NotNull FileEditorManagerEx manager) {
        EditorWindow window = manager.getCurrentWindow();
        if (window != null) {
            return window;
        }
        EditorWindow[] windows = manager.getWindows();
        return windows.length > 0 ? windows[0] : null;
    }

    public static final class Tab1 extends TabNumberIndexAction {
        public Tab1() {
            super(1);
        }
    }

    public static final class Tab2 extends TabNumberIndexAction {
        public Tab2() {
            super(2);
        }
    }

    public static final class Tab3 extends TabNumberIndexAction {
        public Tab3() {
            super(3);
        }
    }

    public static final class Tab4 extends TabNumberIndexAction {
        public Tab4() {
            super(4);
        }
    }

    public static final class Tab5 extends TabNumberIndexAction {
        public Tab5() {
            super(5);
        }
    }

    public static final class Tab6 extends TabNumberIndexAction {
        public Tab6() {
            super(6);
        }
    }

    public static final class Tab7 extends TabNumberIndexAction {
        public Tab7() {
            super(7);
        }
    }

    public static final class Tab8 extends TabNumberIndexAction {
        public Tab8() {
            super(8);
        }
    }

    public static final class Tab9 extends TabNumberIndexAction {
        public Tab9() {
            super(9);
        }
    }
}

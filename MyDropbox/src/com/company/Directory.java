package com.company;

import java.io.IOException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class Directory {

    static Path directoryPath = null;

    Directory(String directoryPath){
        this.directoryPath = Paths.get(directoryPath);
    }

    public String checkDirectory(){
        WatchService watcher = null;
        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
            WatchKey key = null;
            try {
                key = this.directoryPath.register(watcher,
                        ENTRY_CREATE,
                        ENTRY_DELETE,
                        ENTRY_MODIFY);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                if (kind == OVERFLOW) {
                    continue;
                }
                if (kind == ENTRY_CREATE) {
                    key.cancel();
                    try {
                        watcher.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return event.context().toString();
                }
            }
        }
    }
}

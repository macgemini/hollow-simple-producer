/*
 *
 *  Copyright 2016 Netflix, Inc.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */
package pl.pracuj.bigdata.hollow.consumer;

import com.netflix.hollow.api.consumer.HollowConsumer;
import com.netflix.hollow.api.consumer.fs.HollowFilesystemAnnouncementWatcher;
import com.netflix.hollow.api.consumer.fs.HollowFilesystemBlobRetriever;
import com.netflix.hollow.explorer.ui.jetty.HollowExplorerUIServer;
import com.netflix.hollow.history.ui.jetty.HollowHistoryUIServer;
import pl.pracuj.bigdata.hollow.consumer.api.generated.AlreadyApplied;
import pl.pracuj.bigdata.hollow.consumer.api.generated.AlreadyAppliedAPI;
import pl.pracuj.bigdata.hollow.consumer.api.generated.AlreadyAppliedPrimaryKeyIndex;
import pl.pracuj.bigdata.hollow.producer.Producer;

import java.io.File;

public class Consumer {
    
    public static void main(String args[]) throws Exception {
        File publishDir = new File(Producer.SCRATCH_DIR, "publish-dir");
        
        System.out.println("I AM THE CONSUMER.  I WILL READ FROM " + publishDir.getAbsolutePath());

        HollowConsumer.BlobRetriever blobRetriever = new HollowFilesystemBlobRetriever(publishDir);
        HollowConsumer.AnnouncementWatcher announcementWatcher = new HollowFilesystemAnnouncementWatcher(publishDir);
        
        HollowConsumer consumer = HollowConsumer.withBlobRetriever(blobRetriever)
                                                .withAnnouncementWatcher(announcementWatcher)
                                                .withGeneratedAPIClass(AlreadyAppliedAPI.class)
                                                .build();
        
        consumer.triggerRefresh();
        
        hereIsHowToUseTheDataProgrammatically(consumer);
        
        /// start a history server on port 7777
        HollowHistoryUIServer historyServer = new HollowHistoryUIServer(consumer, 7777);
        historyServer.start();
        
        /// start an explorer server on port 7778
        HollowExplorerUIServer explorerServer = new HollowExplorerUIServer(consumer, 7778);
        explorerServer.start();
        
        historyServer.join();
    }

    private static void hereIsHowToUseTheDataProgrammatically(HollowConsumer consumer) {
        /// create an index for AlreadyApplied based on its primary key (Id)
        AlreadyAppliedPrimaryKeyIndex idx = new AlreadyAppliedPrimaryKeyIndex(consumer);

        /// find the movie for a some known ID
        AlreadyApplied foundAlreadyApplied = idx.findMatch(1000004);

        System.out.println("Found: " + foundAlreadyApplied.getOfferId());
    }
    
}

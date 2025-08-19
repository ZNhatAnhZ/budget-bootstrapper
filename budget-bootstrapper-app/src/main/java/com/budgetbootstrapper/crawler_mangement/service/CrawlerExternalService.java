package com.budgetbootstrapper.crawler_mangement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrawlerExternalService {

    //note:
    //1st approach: use the crawl script to save into the database, then there will be a flush event to github to save the image
        // pros: no code changes, batch processing, the script save the records itself
        // cons: the script should only be responsible for crawling, not for saving to the database.
                // the flush event to save to cloud storage is difficult to scale

    //2nd apptoach: the crawler script will call api to save the records into the database, no db connection needed in the script.
        // pros: the script is only responsible for crawling, the api is responsible for saving to the database.
                // greater scalability as there will be a separate backend module for the scrawler. the model will be n crawler scripts - 1 backend scrawler module
        // cons: the script should be able to call the api, so it should have internet connection.

//  private final CrawlerConfig crawlerConfig;
//
//  public void startCrawler() {
//    try {
//      ProcessBuilder pb =
//          new ProcessBuilder(
//              "node",
//              "index.js",
//              JacksonUtil.toString(crawlerConfig.getArgs())); // Command to execute
//      pb.directory(new File(crawlerConfig.getScriptDirectory())) // Set current directory
//          .redirectOutput(ProcessBuilder.Redirect.INHERIT) // Redirect output to console
//          .redirectError(ProcessBuilder.Redirect.INHERIT) // Redirect errors to console
//          .start()
//          .onExit()
//          .thenAccept(
//              p -> {
//                log.info("startCrawler process completed");
//                startCommittingScriptToGithub();
//              });
//    } catch (IOException e) {
//      log.error("Error while running startCrawler", e);
//    }
//  }
//
//  public void startCommittingScriptToGithub() {
//    try {
//      ProcessBuilder pb = new ProcessBuilder("sh", "pushingToGit.sh"); // Command to execute
//      pb.directory(
//              new File(crawlerConfig.getPushingToGitScriptDirectory())) // Set current directory
//          .redirectOutput(ProcessBuilder.Redirect.INHERIT) // Redirect output to console
//          .redirectError(ProcessBuilder.Redirect.INHERIT) // Redirect errors to console
//          .start()
//          .onExit()
//          .thenAccept(p -> log.info("startCommittingScriptToGithub process completed"));
//    } catch (IOException e) {
//      log.error("Error while running startCommittingScriptToGithub", e);
//    }
//  }
}

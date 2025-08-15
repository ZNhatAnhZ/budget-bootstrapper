package com.budgetbootstrapper.animal_news.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class CrawlerExternalService {

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

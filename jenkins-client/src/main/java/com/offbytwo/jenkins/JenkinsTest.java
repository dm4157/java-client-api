package com.offbytwo.jenkins;


import com.offbytwo.jenkins.model.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by msdg on 2019/1/7.
 * Look, there is a moon.
 */
public class JenkinsTest {

    public static void main(String[] args) throws URISyntaxException, IOException {

        String rooUrl = "http://ci.lianjia.com";
        JenkinsServer jenkins = new JenkinsServer(new URI(rooUrl), "qianlimei", "101379Sanctity");
        FolderJob folderJob = new FolderJob("confucius", rooUrl + "/job/confucius/");

//        Map<String, Job> jobs = jenkins.getJobs(new FolderJob("confucius", "http://ci.lianjia.com/job/confucius/"));

//        jobs.forEach((k,v) -> System.out.println(k));

//        Job job = jobs.get("mooc-server-test");
        JobWithDetails jobWithDetail = jenkins.getJob(folderJob, "viviane");
        System.out.println(jobWithDetail);

//        jobWithDetail.getLastBuild().promotionBuild("m-u44");

        List<Action> actions = jobWithDetail.actions();
        for (Action action : actions) {
            System.out.println(action.getName());
            System.out.println(action.getColor());
            System.out.println(action.getUrl());

            ActionWithDetails actionDetail = jobWithDetail.actionDetails(action.getName());

            String config = actionDetail.getPromotionXml();
//            Pattern pattern = Pattern.compile("<hudson\\.tasks\\.Shell>.*</hudson\\.tasks\\.Shell>");
//            Matcher matcher = pattern.matcher(config);
//            while (matcher.find()) {
//                System.out.println(matcher.group());
//            }
//
            Document doc = Jsoup.parse(config, "", Parser.xmlParser());
            Elements elements = doc.getElementsByTag("command");
            Element element = elements.get(0);

            String command = element.text();
            if (command.contains("--limit")) {
                command = command.replaceAll("\\s+--limit\\s+\\d+\\.\\d+\\.\\d+\\.\\d+", " --limit 10.200.17.2333");
            } else {
                command += " --limit 10.200.17.22";
            }
            element = element.text(command);
            actionDetail.modifyPromotionXml(doc.toString());

//            Build actionBuild = actionDetail.getBuildByNumber(actionDetail.getNextBuildNumber() - 1);
//            BuildWithDetails actionBuildDetail = actionBuild.details();
//            ConsoleLog consoleLog = actionBuildDetail.getConsoleOutputText(0);
//            System.out.println(consoleLog.getConsoleLog());
        }
    }
//
//    public static void main(String[] args) {
//        String config = "<hudson.plugins.promoted__builds.PromotionProcess plugin=\"promoted-builds@3.1\">\n" +
//                "<keepDependencies>false</keepDependencies>\n" +
//                "<properties/>\n" +
//                "<scm class=\"hudson.scm.NullSCM\"/>\n" +
//                "<canRoam>true</canRoam>\n" +
//                "<disabled>false</disabled>\n" +
//                "<blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>\n" +
//                "<blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>\n" +
//                "<triggers/>\n" +
//                "<concurrentBuild>false</concurrentBuild>\n" +
//                "<conditions/>\n" +
//                "<icon>star-red</icon>\n" +
//                "<isVisible/>\n" +
//                "<buildSteps>\n" +
//                "<hudson.tasks.Shell>\n" +
//                "<command>\n" +
//                "echo '========' echo $PROMOTED_NUMBER wget_path=\"ftp://ftpadmin:nQDPrUh4@ci.jenkins.ke.com/home/work/jenkins_home/jobs/ZD/jobs/app-push-ui-release/builds/${PROMOTED_NUMBER}/archive/releases/m.zhidaovip.com.tar.gz\" /usr/bin/distrsync --url $wget_path --module apppush.zhidaovip.com --access-token YZQAUUHXMZEISRX52Q7QLAUIFYVB037L1XSTELF89F3CNKIMDCV3TQZKW2WWZM44\n" +
//                "</command>\n" +
//                "</hudson.tasks.Shell>\n" +
//                "<hudson.tasks.Shell>\n" +
//                "<command>\n" +
//                "cd $WORKSPACE/m.zhidaovip.com tar -czf static.tar.gz $WORKSPACE/m.zhidaovip.com/static /usr/bin/distrsync --srcroot static --module s1.ljcdn.com --submodule ruzhidao --access-token YZQAUUHXMZEISRX52Q7QLAUIFYVB037L1XSTELF89F3CNKIMDCV3TQZKW2WWZM44 rm -fr $WORKSPACE/m.zhidaovip.com/static.tar.gz\n" +
//                "</command>\n" +
//                "</hudson.tasks.Shell>\n" +
//                "</buildSteps>\n" +
//                "</hudson.plugins.promoted__builds.PromotionProcess>";
//
//        System.out.println(config);
//        Document doc = Jsoup.parse(config, "", Parser.xmlParser());
//
//        Elements elements = doc.getElementsByTag("command");
//        Element element = elements.get(0);
//        String command = element.text();
//        if (command.contains("--limit")) {
//            command = command.replaceAll("\\s+--limit\\s+\\d+\\.\\d+\\.\\d+\\.\\d+", " --limit 10.200.17.22");
//        } else {
//            command += " --limit 1.1.1.1";
//        }
//        element = element.text(command);
//        System.out.println(doc.toString());
////
////        command = element.text();
////        if (command.contains("--limit")) {
////            command = command.replaceAll("\\s+--limit\\s+\\d+\\.\\d+\\.\\d+\\.\\d+", " --limit 111.111.111.111");
////        } else {
////            command += " --limit 255.255.255.255";
////        }
////        element = element.text(command);
////        System.out.println(doc.toString());
////
////        command = element.text();
////        if (command.contains("--limit")) {
////            command = command.replaceAll("\\s+--limit\\s+\\d+\\.\\d+\\.\\d+\\.\\d+", "");
////        }
////        element = element.text(command);
////        System.out.println(doc.toString());
//    }
}

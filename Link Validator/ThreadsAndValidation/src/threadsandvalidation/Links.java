/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threadsandvalidation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 * @author ALEX STORE
 */
public class Links {

    private String url;
    private static int numberOfLinks;
    private static int numberOfValidLinks = 0;
    private static int numberOfInvalidLinks = 0;

    public Links(String url) {
        this.url = url;
    }

    public Links() {
    }

    public static int getNumberOfValidLinks() {
        return Links.numberOfValidLinks;
    }

    public static void setNumberOfValidLinks(int numberOfValidLinks) {
        Links.numberOfValidLinks = numberOfValidLinks;
    }

    public static int getNumberOfInvalidLinks() {
        return Links.numberOfInvalidLinks;
    }

    public static void setNumberOfInvalidLinks(int numberOfInvalidLinks) {
        Links.numberOfInvalidLinks = numberOfInvalidLinks;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static int getNumberOfLinks() {
        return numberOfLinks;
    }

    public static void setNumberOfLinks(int numberOfLinks) {
        Links.numberOfLinks = numberOfLinks;
    }

    public boolean OneLinkValidation() {
        boolean valid = false;
        try {
            Document request = Jsoup.connect(this.getUrl()).get();
            valid = true;
        } catch (MalformedURLException ex) { //This occurs when the URL is wrongly formed
            System.out.println("URL Wrong Format");
        } catch (HttpStatusException ex) {  // This occurs when the page can not found
            System.out.println("Page Not Found");
        } catch (IOException ex) { // This occurs when the server can not be found
            System.out.println("Server Not Found");
        }

        return valid;
    }
    //
    //

    public void validation(int cores, int depth, int finalDepth) throws MalformedURLException {
        Links Lm = new Links();
        if (OneLinkValidation()) {
            System.out.println("Valid Link " + url);
            Links.setNumberOfValidLinks(Links.getNumberOfValidLinks() + 1);
            if (depth == finalDepth) {
                return;
            } else {
                ArrayList<Links> LinksInsideThisURL;
                LinksInsideThisURL = this.LinksArray();
                int i;
                for (i = 0; i < LinksInsideThisURL.size(); i++) {
                    if (cores != 1) {
                        Validation thread = new Validation(LinksInsideThisURL.get(i), depth + 1, finalDepth);
                        Validation.executor.execute((Runnable) thread);
                    } else {
                        LinksInsideThisURL.get(i).validation(cores, depth + 1, finalDepth);
                    }

                }
            }
        } else {
            System.out.println("Invalid Link " + url);
            setNumberOfInvalidLinks(Links.getNumberOfInvalidLinks() + 1);
        }
    }

    public ArrayList<Links> LinksArray() throws MalformedURLException {
        ArrayList<Links> l = new ArrayList<>();
        l.add(new Links(this.url));
        Document HTMLCode = null;
        try {
            HTMLCode = Jsoup.connect(url).get();
        } catch (IOException ex) {
            System.err.println("Error Extracting Link");
            setNumberOfLinks(1);
            setNumberOfInvalidLinks(1);
        }
        Elements AllLinks = HTMLCode.select("a[href]"); // extract only links
        numberOfLinks = AllLinks.size();
        String formatlink;
        URL u = null;
        try {
            u = new URL(url);
        } catch (MalformedURLException ex) {
            System.err.println("Error getting Host Link");
        }
        for (int i = 1; i < AllLinks.size(); i++) {
            formatlink = AllLinks.get(i).attr("href");
            l.add(new Links(AllLinks.get(i).attr("href")));

            if (!formatlink.startsWith("http")) {
                l.get(i).joinHostwhithIndex(this);
            }
        }
        return l;
    }

    public void joinHostwhithIndex(Links L) throws MalformedURLException {
        URL u = new URL(L.getUrl());
        String MainHost = u.getProtocol() + "://" + u.getHost();
        this.setUrl(MainHost + this.getUrl());
    }

}

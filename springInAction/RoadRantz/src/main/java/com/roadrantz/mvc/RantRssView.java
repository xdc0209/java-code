package com.roadrantz.mvc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.roadrantz.domain.Rant;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;

public class RantRssView extends AbstractView {
  private String author;
  private String title;
  private String description;
  private String link;
  
  public RantRssView() {}
  
  protected void renderMergedOutputModel(Map model, 
      HttpServletRequest request, HttpServletResponse response) 
      throws Exception {

    SyndFeed feed = createFeed();
    List rants = (List)model.get("rants");
    List entries = new ArrayList();
    
    for (Iterator iter = rants.iterator(); iter.hasNext();) {
      Rant rant = (Rant) iter.next();
      entries.add(createEntry(rant));
    }

    feed.setEntries(entries);
    
    SyndFeedOutput output = new SyndFeedOutput();
    output.output(feed, response.getWriter());
  }

  private SyndEntry createEntry(Rant rant) {
    SyndEntry entry = new SyndEntryImpl();
    
    entry.setTitle("title?");
    entry.setLink("link");
    entry.setPublishedDate(rant.getPostedDate());
    SyndContent content = new SyndContentImpl();
    content.setType("text/html");
    content.setValue(rant.getRantText());
    entry.setDescription(content);
    
    return entry;
  }
  
  private SyndFeed createFeed() {
    SyndFeed feed = new SyndFeedImpl();
    feed.setFeedType("rss_1.0"); 
    feed.setAuthor(author);
    feed.setTitle(title);
    feed.setDescription(description);
    feed.setLink(link);
    
    return feed;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}

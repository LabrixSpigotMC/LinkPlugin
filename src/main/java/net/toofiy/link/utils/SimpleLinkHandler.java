package net.toofiy.link.utils;

import com.google.common.collect.Lists;
import net.toofiy.link.LinkBootstrap;
import net.toofiy.link.utils.obj.Link;
import net.toofiy.link.utils.obj.LinkHandler;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import java.util.UUID;

public class SimpleLinkHandler implements LinkHandler {

    private final List<Link> cachedLinks;
    private List<Link> links;

    public SimpleLinkHandler(){
        cachedLinks = Lists.newArrayList();
        links = Lists.newArrayList();

        loadLinksFromConfiguration();
    }

    @Override
    public void loadLinksFromConfiguration() {
        final List<String> uuidList = LinkBootstrap.getInstance().getConfig().getStringList("uuids");

        for (String uuid : uuidList) {
            cachedLinks.add(new Link() {
                @Override
                public String getName() {
                    return LinkBootstrap.getInstance().getConfig().getString("links." + uuid + ".name");
                }

                @Override
                public String getURL() {
                    return LinkBootstrap.getInstance().getConfig().getString("links." + uuid + ".url");
                }

                @Override
                public UUID getUUID() {
                    return UUID.fromString(uuid);
                }
            });
        }

        links = cachedLinks;
    }

    @Override
    public void addLink(Link link) {
        cachedLinks.add(link);

        final List<String> uuidList = LinkBootstrap.getInstance().getConfig().getStringList("uuids");
        uuidList.add(link.getUUID().toString());

        LinkBootstrap.getInstance().getConfig().set("links." + link.getUUID().toString() + ".name", link.getName());
        LinkBootstrap.getInstance().getConfig().set("links." + link.getUUID().toString() + ".url", link.getURL());

        LinkBootstrap.getInstance().getConfig().set("uuids", uuidList);

        LinkBootstrap.getInstance().getConfig().set("currentLinks", (LinkBootstrap.getInstance().getConfig().getInt("currentLinks") + 1));
        LinkBootstrap.getInstance().saveConfig();

        if(cachedLinks.size() >= LinkBootstrap.getInstance().getConfig().getInt("currentLinks"))
            links = cachedLinks;
    }

    @Override
    public void addLink(String name, String url, UUID uuid) {
        addLink(new Link() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getURL() {
                return url;
            }

            @Override
            public UUID getUUID() {
                return uuid;
            }
        });
    }


    @Override
    public Link getLink(UUID uuid) {
        for (Link cachedLink : cachedLinks)
            if(uuid.equals(cachedLink.getUUID()))
                return cachedLink;
        return null;
    }

    @Override
    public List<Link> getLinks() {
        return this.links;
    }
}

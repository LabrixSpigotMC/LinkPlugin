package net.toofiy.link.utils.obj;

import java.util.List;
import java.util.UUID;

public interface LinkHandler {

    void loadLinksFromConfiguration();

    void addLink(final Link link);

    void addLink(final String name, final String url, final UUID uuid);

    Link getLink(final UUID uuid);

    List<Link> getLinks();

}

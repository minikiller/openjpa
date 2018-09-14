package org.apache.openjpa.lib.meta;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class OSGiBundleMetaDataIterator implements MetaDataIterator, MetaDataFilter.Resource {
    private InputStream stream;
    private Bundle bundle;
    private Enumeration<URL> entries;
    private MetaDataFilter filter;
    private URL entry;
    private URL last;
    private byte[] buf;
    public OSGiBundleMetaDataIterator(URL bundleUrl, MetaDataFilter filter) {
        BundleContext ctx = FrameworkUtil.getBundle(OSGiBundleMetaDataIterator.class).getBundleContext();
        long bundleId = Long.parseLong(bundleUrl.getHost().substring(0, bundleUrl.getHost().indexOf(".")));
        this.bundle = ctx.getBundle(bundleId);
        entries = this.bundle.findEntries("/", "*.class", true);
        this.filter = filter;
    }
    @Override
    public boolean hasNext() throws IOException {
        if (entries == null) {
            return false;
        }
        if (entry != null) {
            return true;
        }
        //        if (buf == null && last != null)
//            _stream.closeEntry();
        last = null;
        buf = null;
        if (!entries.hasMoreElements()) {
            return false;
        }
        URL tmp;
        while ( entry == null && (entries.hasMoreElements() && (tmp = this.entries.nextElement()) != null)) {
            entry = tmp;
            stream = entry.openStream();
            if (filter != null && !this.filter.matches(this)) {
                entry = null;
            }
        }
        return entry != null;
    }
    @Override
    public Object next() throws IOException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        String name = entry.toString();
        last = entry;
        entry = null;
        return name;
    }
    @Override
    public InputStream getInputStream() throws IOException {
        if (last == null)
            throw new IllegalStateException();
        return last.openStream();
    }
    @Override
    public File getFile() throws IOException {
        return null;
    }
    @Override
    public void close() {
    }
    @Override
    public String getName() {
        return entry.toString();
    }
    @Override
    public byte[] getContent() throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        for (int r; (r = stream.read(buf)) != -1; bout.write(buf, 0, r)) ;
        buf = bout.toByteArray();
        stream.close();
        return buf;
    }
}

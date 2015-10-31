package net.servehttp.bytecom.commons.parse;

import java.io.Serializable;

public interface IParse<T, E> extends Serializable {

    E parse(T t) throws Exception;
}

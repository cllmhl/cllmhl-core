package it.fe.cllmhl.core;

import java.util.Locale;

public interface IMessage {
    MessageLevel getMessageLevel();

    String getMessage(Locale pLocale);
}

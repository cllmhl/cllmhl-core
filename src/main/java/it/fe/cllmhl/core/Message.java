package it.fe.cllmhl.core;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Message implements IMessage, Serializable {

    private static final long serialVersionUID = 1L;
    private MessageLevel messageLevel;
    private String resourceBundle;
    private String resourceBundleKey;
    private String[] args;

    public Message(MessageLevel messageLevel, String resourceBundle, String resourceBundleKey) {
        this(messageLevel, resourceBundle, resourceBundleKey, (String[]) null);
    }

    public Message(MessageLevel messageLevel, String resourceBundle, String resourceBundleKey, String... args) {
        this.messageLevel = messageLevel;
        this.resourceBundle = resourceBundle;
        this.resourceBundleKey = resourceBundleKey;
        this.args = args;
    }

    @Override
    public String getMessage(Locale pLocale) {
        ResourceBundle lResourceBundle = ResourceBundle.getBundle(resourceBundle, pLocale);
        return MessageFormat.format(lResourceBundle.getString(resourceBundleKey), (Object[]) args);
    }

    @Override
    public MessageLevel getMessageLevel() {
        return messageLevel;
    }
}

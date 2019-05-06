function loadInjectionId(onInjectionIdLoaded, onInjectionIdChangeDetected) {
    getCachedInjectionId(function (cachedInjectionId) {
        var hasCachedInjectionId = isInjectionIdValid(cachedInjectionId);
        if (hasCachedInjectionId) {
            onInjectionIdLoaded(cachedInjectionId);
        }
        if (isNativeMessagingSupported()) {
            loadInjectionIdFromNativeMessaging(function (injectionId) {
                if (cachedInjectionId === injectionId)
                    return;
                saveInjectionIdToCache(injectionId, function () {
                    if (hasCachedInjectionId) {
                        onInjectionIdChangeDetected();
                    }
                    else {
                        onInjectionIdLoaded(injectionId);
                    }
                });
            });
        }
        else {
            if (!hasCachedInjectionId) {
                var injectionId = util.generateUniqueId();
                saveInjectionIdToCache(injectionId);
                onInjectionIdLoaded(injectionId);
            }
        }
    });
}
function isInjectionIdValid(injectionId) {
    return (typeof injectionId === "string") && (injectionId.length > 0);
}
function isNativeMessagingSupported() {
    return !!chrome.runtime.connectNative;
}
function loadInjectionIdFromNativeMessaging(callback) {
    var messageReceived = false;
    var requiredProtocolVersion = 1;
    var hostName = "com.kaspersky." + chrome.runtime.id.replace("@", ".") + ".host";
    var port = chrome.runtime.connectNative(hostName);
    port.onMessage.addListener(onPacketReceived);
    port.onDisconnect.addListener(onServerDisconnected);
    function sendResultToNativeServer(result) {
        port.postMessage({ "protocolVersion": requiredProtocolVersion, "payload": { "result": result } });
    }
    function onPacketReceived(packet) {
        port.onMessage.removeListener(onPacketReceived);
        messageReceived = true;
        if (packet.protocolVersion != requiredProtocolVersion) {
            console.error("Invalid protocol version", packet.protocolVersion);
            sendResultToNativeServer("Invalid protocol");
            return;
        }
        var injectionId = packet.payload.injectionId;
        if (isInjectionIdValid(injectionId)) {
            sendResultToNativeServer('ok');
            callback(injectionId);
        }
        else {
            console.error("Invalid injection id: ", injectionId);
            sendResultToNativeServer("Invalid injection id");
            return;
        }
    }
    function onServerDisconnected() {
        if (!messageReceived) {
            console.error("Native messaging server early disconnection, no message received");
        }
    }
}
function getCachedInjectionId(callback) {
    chrome.storage.local.get('injectionId', function (result) {
        callback(result.injectionId);
    });
}
function saveInjectionIdToCache(injectionId, callback) {
    chrome.storage.local.set({ 'injectionId': injectionId }, callback);
}

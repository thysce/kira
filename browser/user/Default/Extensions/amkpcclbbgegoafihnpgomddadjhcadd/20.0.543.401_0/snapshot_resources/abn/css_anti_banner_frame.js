var helpLink = "";
var savedLocales;


window.FrameObject.onLocalize = function (locales) 
{
	savedLocales = locales;
	LocalizeElement("enableOnThisSiteButton", locales["AntiBannerEnableOnThisSite"])
	LocalizeElement("disableOnThisSiteButton", locales["AntiBannerDisableOnThisSite"])
	LocalizeElement("showSettingsButton", locales["AntiBannerSettings"])
	LocalizeElement("showHelpButton", locales["AntiBannerHelp"])
	LocalizeElement("componentNameText", locales["AntiBannerComponentName"])
	LocalizeElement("addToBlockListButton", locales["AntiBannerContextMenuPrompt"])
	LocalizeElement("blockStatus", locales["AntiBannerStatusOff"])

	helpLink = encodeURI(locales["AntiBannerHelpUrlSettings"]);
}

window.FrameObject.onGetData = function(data) 
{
	LocalizeElement("blockedByPartner", savedLocales["AntiBannerOffAsPartnerDescription"].replace("%DomainName%", data.domainName))
	if (IsDefined(data.disabledByPartner)) // backward compatibility condition. Remove after chrome plugin release
		document.getElementById("enableOnThisSiteButton").disabled = data.disabledByPartner;
}

function AddOnClickHandlerById(id, handler)
{
	var element = document.getElementById(id);
	if (element)
	{
		AddEventListener(element, "click", handler);
	}
	else
	{
		setTimeout(function () { AddOnClickHandlerById(id, handler); }, 500);
	}
}

AddEventListener(window, "load",
	function()
	{
		AddOnClickHandlerById("enableOnThisSiteButton", OnSetBlockStatus);
		AddOnClickHandlerById("disableOnThisSiteButton", OnSetBlockStatus);
		AddOnClickHandlerById("addToBlockListButton", OnAddToBlockList);
		AddOnClickHandlerById("showSettingsButton", OnShowAntiBannerSettings);
		AddOnClickHandlerById("showHelpButton", OnAntiBannerHelp);
	});
	
AddEventListener(document, "contextmenu", OnContextMenuTooltip);
AddEventListener(document, "mousedown", OnContextMenuTooltip);

function IsContextMenuEvent(evt)
{
	return evt.ctrlKey && (evt.button === 2 || evt.type === "contextmenu");
}

function OnContextMenuTooltip(evt)
{
	if (IsContextMenuEvent(evt))
		StopProcessingEvent(evt);
}

function OnSetBlockStatus()
{
	SendClose(1);
}

function OnAddToBlockList()
{
	SendClose(2);
}

function OnShowAntiBannerSettings()
{
	SendClose(3);
}

function OnAntiBannerHelp()
{
	window.open(helpLink);
	SendClose(4);
}

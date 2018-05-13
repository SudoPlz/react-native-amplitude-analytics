#import "AmplitudeSDK.h"

@implementation RNAmplitudeSDK

RCT_EXPORT_MODULE()

// initializes Amplitude
RCT_EXPORT_METHOD(initialize:(NSString* )writeKey setTrackSessionEvents:(BOOL) trackSessionEvents)
{
     [Amplitude instance].trackingSessionEvents = trackSessionEvents;
     [[Amplitude instance] initializeApiKey: writeKey];
}

RCT_EXPORT_METHOD(setUserId:(NSString *)userId)
{
     [[Amplitude instance] setUserId:userId];
}

RCT_EXPORT_METHOD(setUserProperties:(NSDictionary *)properties)
{
     [[Amplitude instance] setUserProperties:properties];
}

RCT_EXPORT_METHOD(setOptOut:(BOOL) optOut)
{
    [[Amplitude instance] setOptOut:optOut];
}

RCT_EXPORT_METHOD(clearUserProperties)
{
     [[Amplitude instance] clearUserProperties];
}

RCT_EXPORT_METHOD(regenerateDeviceId)
{
     [[Amplitude instance] regenerateDeviceId];
}

RCT_EXPORT_METHOD(logEvent:(NSString *)event)
{
     [[Amplitude instance] logEvent: event];
}

RCT_EXPORT_METHOD(logEventWithProps:(NSString *)eventKey properties:(NSDictionary *)properties)
{
     [[Amplitude instance] logEvent: eventKey withEventProperties: properties];
}

RCT_EXPORT_METHOD(logRevenue:(NSString *)productIdentifier quantity:(int)quantity price:(double)price receipt:(NSString*)receipt)
{
     AMPRevenue *revenue = [[[AMPRevenue revenue] setProductIdentifier:productIdentifier] setQuantity: quantity];
     [revenue setPrice:[NSNumber numberWithDouble:price]];
     [revenue setReceipt:[receipt dataUsingEncoding:NSUTF8StringEncoding]];
     [[Amplitude instance] logRevenueV2:revenue];
}

@end

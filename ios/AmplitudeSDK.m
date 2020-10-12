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

RCT_REMAP_METHOD(getDeviceId, getDeviceIdWithResolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    NSString *deviceId = [[Amplitude instance] getDeviceId];
    resolve(deviceId);
}

RCT_REMAP_METHOD(getSessionId, getSessionIdWithResolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject)
{
    long long sessionId = [[Amplitude instance] getSessionId];
    NSNumber *sessionIdNsNumber = [NSNumber numberWithLongLong: sessionId]
    resolve(sessionIdNsNumber);
}

RCT_EXPORT_METHOD(setDeviceId:(NSString *)deviceId)
{
     [[Amplitude instance] setDeviceId: deviceId];
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

RCT_EXPORT_METHOD(logEventWithTimestamp:(NSString *)eventKey timestamp:(nonnull NSNumber *)timestamp properties:(NSDictionary *)properties)
{
     [[Amplitude instance] logEvent: eventKey withEventProperties: properties withGroups: [NSMutableDictionary dictionary] withTimestamp: timestamp outOfSession: NO];
}

RCT_EXPORT_METHOD(logRevenue:(NSString *)productIdentifier quantity:(int)quantity price:(double)price receipt:(NSString*)receipt)
{
     AMPRevenue *revenue = [[[AMPRevenue revenue] setProductIdentifier:productIdentifier] setQuantity: quantity];
     [revenue setPrice:[NSNumber numberWithDouble:price]];
     [revenue setReceipt:[receipt dataUsingEncoding:NSUTF8StringEncoding]];
     [[Amplitude instance] logRevenueV2:revenue];
}

RCT_EXPORT_METHOD(logRevenueV2:(NSDictionary *)properties)
{
    NSString *productId = [properties valueForKey:@"productId"];
    NSNumber *quantityObj = [properties valueForKey:@"quantity"];
    NSInteger quantity = quantityObj ? [quantityObj integerValue] : 1;
    NSNumber *price = [properties valueForKey:@"price"];
    NSString *receipt = [properties valueForKey:@"receipt"];
    NSString *revenueType = [properties valueForKey:@"revenueType"];
    NSDictionary *eventProperties = [properties valueForKey:@"eventProperties"];
    AMPRevenue *revenue = [[[AMPRevenue revenue] setProductIdentifier:productId] setQuantity:quantity];
    [revenue setPrice:price];
    [revenue setReceipt:[receipt dataUsingEncoding:NSUTF8StringEncoding]];
    [revenue setRevenueType:revenueType];
    [revenue setEventProperties:eventProperties];
    [[Amplitude instance] logRevenueV2:revenue];
}

RCT_EXPORT_METHOD(addToUserProperty:(NSString *)property value:(int)value)
{
     AMPIdentify *identify = [[AMPIdentify identify] add:property value:[NSNumber numberWithInt:value]];
     [[Amplitude instance] identify:identify];
}

RCT_EXPORT_METHOD(setUserPropertyOnce:(NSString *)property value:(NSString *)value)
{
     AMPIdentify *identify = [[AMPIdentify identify] setOnce:property value:value];
     [[Amplitude instance] identify:identify];
}

@end


#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#else
#import "RCTBridgeModule.h"
#endif

#if __has_include(<Amplitude/Amplitude.h>)
#import <Amplitude/Amplitude.h>
#else
#import "Amplitude.h"
#endif


#if __has_include(<Amplitude/AMPRevenue.h>)
#import <Amplitude/AMPRevenue.h>
#else
#import "AMPRevenue.h"
#endif

@interface RNAmplitudeSDK : NSObject <RCTBridgeModule>

@end

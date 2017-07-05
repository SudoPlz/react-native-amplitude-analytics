
# react-native-amplitude-analytics

[![npm version](https://badge.fury.io/js/react-native-amplitude-analytics.svg)](https://badge.fury.io/js/react-native-amplitude-analytics)

## Dependencies

`react-native` version `>0.40`

## Installation

`npm i react-native-amplitude-analytics --save`

`react-native link react-native-amplitude-analytics`

#### iOS installation

1. Open your app `.xcodeproj` file
2. Add the following line to your "Podfile": `pod 'Amplitude-iOS', '~> 3.14.1'`
3. run `pod install`
4. Run your project (`Cmd+R`)

#### Android Manual installation

1. Open up `android/app/src/main/java/[...]/MainApplication.java`
  - Add 

    ```java
    import com.sudoplz.reactnativeamplitudeanalytics.RNAmplitudeSDKPackage;
    ```

   to the imports at the top of the file.
   
  - Add 

    ```java
    new RNAmplitudeSDKPackage(),
    ``` 

  to the list returned by the `getPackages()` method
  
2. Append the following lines to `android/settings.gradle`:

  	```gradle
  	include ':react-native-amplitude-analytics'
    project(':react-native-amplitude-analytics').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-amplitude-analytics/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:

  	```gradle
    compile project(':react-native-amplitude-analytics')
  	```

## Usage 

  ```javascript

  import RNAmplitute from 'react-native-amplitude-analytics';

  ```


### Example 

```javascript
class testApp extends Component {
  constructor() {
    super();
	 const amplitude = new RNAmplitute('Your Amplitude key');
	 
	 // log an event
	 amplitude.amplitude(eventName);
	 
	 // log an event with data
	 amplitude.amplitude(eventName, { foo: bar });
	 
	 // set the user id
	 amplitude.setUserId('1D32FS45');
	 
 	 // set user props
	 amplitude.setUserProperties({ hairColor: 'brown' });
	 
	 // log revenue
	 amplitude.logRevenue(productIdentifier, quantity, amount)
  }
  ...
}
```

there's also an example project [here](https://github.com/SudoPlz/react-native-amplitude-analytics/tree/master/Example).

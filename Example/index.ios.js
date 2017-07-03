/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View
} from 'react-native';

import Amplitude from 'react-native-amplitude-analytics';

export default class Example extends Component {
  render() {

    const trackSessionEvents = true; // https://amplitude.zendesk.com/hc/en-us/articles/115003970027#tracking-events
    const amplitude = new Amplitude("Your Amplitude ID goes here", trackSessionEvents);
    alert(`amplitude: ${JSON.stringify(amplitude)}`)
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>
          Hello Amplitute
        </Text>
        <Text style={styles.instructions}>
          Your analytics should now be up and running
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('Example', () => Example);

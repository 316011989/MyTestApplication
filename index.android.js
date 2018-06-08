import React, {
  Component,
} from 'react';
//引入component和UI组件
import {
  AppRegistry,
} from 'react-native';
import { StackNavigator } from 'react-navigation';

//主页
import MainActivity from './module_coach/CoachMain'
//车次列表
import CoachLineListActivity from './module_coach/CoachLineList';

//总导航
const SimpleApp = StackNavigator({
  Main: { screen: MainActivity },
  Lines: { screen: CoachLineListActivity },
});

//主入口
AppRegistry.registerComponent('mytestapplication', () => SimpleApp);
import React, {
  Component,
} from 'react';
//引入component和UI组件
import {
  AppRegistry,
} from 'react-native';
import { createStackNavigator } from 'react-navigation';

//主页
import MainActivity from './module_coach/CoachMain'
//车次列表
import CoachLineListActivity from './module_coach/CoachLineList';
//填写订单
import WriteOrderActivity from './module_coach/WriteOrder';
//订单详情
import OrderDetailActivity from './module_coach/OrderDetail';

//总导航
const RouteCenter = createStackNavigator({
  Main: { screen: MainActivity },
  Lines: { screen: CoachLineListActivity },
  Order: { screen: WriteOrderActivity },
  Detail: { screen: OrderDetailActivity },
}, {
    initialRouteName: 'Main', //导航初始页面
  });


//主入口
AppRegistry.registerComponent('mytestapplication', () => RouteCenter);
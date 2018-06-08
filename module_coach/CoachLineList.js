import React, {
    Component,
} from 'react';
//引入component和UI组件
import {
    Text,
    View,
    StyleSheet,
    Image,
    FlatList,
    TouchableOpacity,
} from 'react-native';

var REQUEST_URL = 'https://apis.qianbao.com/xingyun/v1/bus/ticket/train?access_token=7ac5dd5af6d59f78529fae9582c5bdc0419fb655';

export default class CoachLineListActivity extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loaded: false,
            data: [],
        }
        this.fetchData = this.fetchData.bind(this);
        this.renderLine = this.renderLine.bind(this);
    }

    //overwrite的方法,相当于loadfinish
    componentDidMount() {
        this.fetchData();
    }

    //渲染
    render() {
        if (!this.state.loaded) {
            return this.renderLoadingView();
        }

        return (
            <View style={styles.container}>
                <View style={{ flexDirection: 'row', justifyContent: 'space-between', padding: 10 }}>
                    <Text style={{ textAlign: 'left', color: '#ff600a' }}>前一天</Text>
                    <Text style={{ textAlign: 'center' }}>06月12日 星期二</Text>
                    <Text style={{ textAlign: 'right', color: '#ff600a' }}>后一天</Text>
                </View>
                <View style={{ flexDirection: 'row', justifyContent: 'space-around', padding: 10, backgroundColor: 'white', borderTopWidth: 0.5, borderBottomWidth: 0.5, borderColor: '#DDDDDD', }}>
                    <Text style={{ textAlign: 'center' }}>出发(早-晚)</Text>
                    <Text style={{ textAlign: 'center' }}>按价格(高-低)</Text>
                </View>
                <FlatList
                    data={this.state.data}
                    renderItem={this.renderLine}
                    style={{ flex: 1, margin: 10 }}
                />
            </View>
        );
    }

    //网络请求
    fetchData() {
        var requestOptional = {
            method: 'POST',
            //表单参数
            // headers: {
            //     'Content-Type': 'application/x-www-form-urlencoded'
            // },
            //json参数
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            //参数
            body: '{"depCity":"北京","depDate":"2018-06-12","arrCity":"上海"}'
        };

        fetch(REQUEST_URL, requestOptional)
            .then((response) => response.json())
            .then((responseData) => {
                if (responseData.code == 100) {
                    this.setState({
                        data: this.state.data.concat(responseData.data[0].list),
                        loaded: true,
                    });
                } else {
                    alert(responseData.message);
                }
                console.log(responseData)
            })
            .catch((error) => {
                alert("网络请求错误");
                console.log(error)
            });
    }

    //渲染加载中效果
    renderLoadingView() {
        return (
            <View style={styles.container}>
                <Text>加载车次数据...</Text>
            </View>
        );
    }

    //渲染车次列表
    renderLine(line) {
        return (
            <View style={{ flex: 1, backgroundColor: 'white', padding: 15, flexDirection: 'column', borderWidth: 0.5, borderRadius: 3, borderColor: '#DDDDDD', }}>
                <View style={{ flexDirection: 'row', justifyContent: 'space-between', backgroundColor: 'white' }}>
                    <Text style={{ fontSize: 26, marginBottom: 8, textAlign: 'center', color: '#ff600a' }}>{line.item.depTime}</Text>
                    <Text style={{ fontSize: 29, marginBottom: 8, textAlign: 'center', color: '#ff600a' }}>{'￥' + line.item.price}</Text>
                </View>
                <View style={{ flexDirection: 'row', justifyContent: 'space-between', backgroundColor: 'white' }}>
                    <Text style={{ fontSize: 15, marginBottom: 8, textAlign: 'center' }}>{line.item.depStation}</Text>
                    <Text style={{ fontSize: 15, marginBottom: 8, textAlign: 'center' }}> </Text>
                </View>
                <View style={{ flexDirection: 'row', justifyContent: 'space-between', backgroundColor: 'white' }}>
                    <Text style={{ fontSize: 15, textAlign: 'center' }}>{line.item.arrStation}</Text>
                    <TouchableOpacity style={{ backgroundColor: '#ff600a', paddingTop: 3, paddingBottom: 3, paddingLeft: 12, paddingRight: 12, borderRadius: 3 }} onPress={this.order.bind(this)}>
                        <Text style={{ fontSize: 15, textAlign: 'center', color: 'white', }}>预订</Text>
                    </TouchableOpacity>
                </View>
            </View>
        );
    }

    //预订
    order() {
        this.props.navigation.navigate('Order');
    }

    //标题栏属性
    static navigationOptions = {
        // 导航栏的标题, 可以是字符串也可以是个组件 会覆盖 title 的值 
        headerTitle: (<Text style={{ color: "#000", fontSize: 16, alignSelf: 'center', textAlign: 'center', flex: 1 }}> {'车次列表'}</Text>),
        //右边按钮
        headerRight: (
            <View >
                <Text style={{ padding: 10 }} onPress={() => alert("hello")}>取消</Text>
            </View>
        ),
        //右边按钮
        headerLeft: (
            <View >
                <Image source={require('./icon/icon_back.png')} style={{ width: 20, height: 20, marginLeft: 8, marginRight: 8 }} onPress={() => alert("返回")}></Image>
            </View>
        ),
        //导航栏的title的style
        headerTitleStyle: {
            color: "#000", fontSize: 16, alignSelf: 'center', textAlign: 'center', flex: 1
        },
        //是否允许右滑返回，在iOS上默认为true，在Android上默认为false
        gesturesEnabled: true,
    };
}

const styles = StyleSheet.create({
    flex: {
        flex: 1
    },
    container: {
        flex: 1,//权重
        flexDirection: 'column',//布局方向
        justifyContent: 'flex-start',//内容相对容器位置
        // alignItems: 'center',//
        backgroundColor: '#F6F6F6',//背景颜色
    },
    orangeText: { color: '#ff600a' },

});
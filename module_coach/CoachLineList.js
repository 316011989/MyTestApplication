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
} from 'react-native';

var REQUEST_URL = 'http://apis.qianbao.com/xingyun/v1/bus/ticket/line?depCity=枣庄&depCityCode=534';

export default class CoachLineListActivity extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loaded: false,
            data: [],
        }
        this.fetchData = this.fetchData.bind(this);
        console.log("json");
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
            <FlatList
                data={this.state.data[0].arrCityList}
                renderItem={this.renderLine}
                style={styles.list}
            />
        );
    }

    //网络请求
    fetchData() {
        var requestOptional = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'account=xmg&pwd=123'
        };

        fetch(REQUEST_URL, requestOptional)
            .then((response) => response.json())
            .then((responseData) => {
                // 注意，这里使用了this关键字，为了保证this在调用时仍然指向当前组件，我们需要对其进行“绑定”操作
                this.setState({
                    data: this.state.data.concat(responseData.data),
                    loaded: true,
                });
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
                <Text>
                    Loading datas...
              </Text>
            </View>
        );
    }

    //渲染电影内容
    renderLine(line) {
        return (
            <View style={styles.container}>
                {/* <Image
                    source={{ uri: line.item.posters.thumbnail }}
                    style={styles.thumbnail}
                /> */}
                <View style={styles.container}>
                    <Text style={styles.title}>{line.item.arrCity}</Text>
                    <Text style={styles.year}>{line.item.arrCityCode}</Text>
                </View>
            </View>
        );
    }

    //标题栏属性
    static navigationOptions = {
        // 展示数据 "`" 不是单引号 
        // title: `汽车票`,
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
                <Image source={require('./icon/icon_back.png')} style={{ width: 20, height: 20, marginLeft: 8, marginRight: 8 }}></Image>
                {/* <Text style={{padding:10}} onPress={() => alert("hello")}>返回</Text> */}
            </View>
        ),
        // //左上角的返回键文字, 默认是上一个页面的title  IOS 有效
        // headerBackTitle: "返回",
        // //导航栏的style
        // headerStyle: {
        //   backgroundColor: '#fff',
        // },
        //导航栏的title的style
        headerTitleStyle: {
            color: "#000", fontSize: 16, alignSelf: 'center', textAlign: 'center', flex: 1
        },
        // //按压返回按钮显示的颜色 API > 5.0 有效
        // headerPressColorAndroid: 'blue',
        // //返回按钮的颜色
        // headerTintColor: 'red',
        //是否允许右滑返回，在iOS上默认为true，在Android上默认为false
        gesturesEnabled: true,
    };
}

const styles = StyleSheet.create({
    flex: {
        flex: 1
    },
    list: {
        paddingTop: 20,//内部距离顶部
        backgroundColor: '#F5FCFF',
    },
    container: {
        flex: 1,//权重
        flexDirection: 'row',//布局方向
        justifyContent: 'center',//内容相对容器位置
        alignItems: 'center',//
        backgroundColor: '#F5FCFF',//背景颜色
    },
    title: {
        fontSize: 20,//字体大小
        marginBottom: 8,//距离下边距
        textAlign: 'center',//文字位置
    },
    year: {
        textAlign: 'center',
    },
});

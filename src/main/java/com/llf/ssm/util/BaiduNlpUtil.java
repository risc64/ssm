package com.llf.ssm.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.baidu.aip.nlp.AipNlp;

public class BaiduNlpUtil {

	public static final String APP_ID = "10874793";
	public static final String API_KEY = "wvASAQeWprhtXGCI4E2hn44G";
	public static final String SECRET_KEY = "rxd1FgHp48yy3iKrSE0Gffb2TCvM6WOz";
	public static String ACCESS_TOKEN = null;

	private static String[] BLACK_LIST = { "按照", "被告", "按自动撤回上诉处理", "案件受理费", "被告承担", "被上诉人", "被申请人", "本案", "本案现已审理终结", "本案争议", "本来", "本人", "本院", "本院认为", "标明", "并不", "并非", "并且", "并未", "并无", "驳回上诉维持原判", "不成", "不承担", "不存在", "不到", "不服", "不好", "不经", "不具备", "不能", "不能成立", "不能证明", "不如", "不是", "不应", "不予认可", "不予支持", "不只", "不知", "不止", "部分", "裁判", "采信", "参加", "查明", "撤销", "陈述", "成立", "承担", "出来", "出去", "出生", "除了", "处于", "传票传唤", "此时", "此事", "从而", "达成", "答辩", "答辩期间", "答辩状", "担任审判长", "但是", "当场", "当初", "当年", "当时", "当事人", "导致", "到庭", "得到", "递交", "第一", "第一款", "对此", "对方", "对方当事人", "对于", "多年", "多项", "而且", "而是", "二审", "二审案件受理费", "发展", "法定程序", "法规", "法律程序", "法律法规", "法律途径", "法院", "范围", "范围内", "方式", "方位", "非常", "分别", "否则", "负担", "副本", "该院", "改进", "改判", "告诉", "各类", "各项", "各种", "各种证据", "各自", "给付义务", "根据", "更加", "更新",
			"更有", "公告", "公开", "共计", "共同", "构成", "规定", "规则", "国家规定", "过程", "还给", "还是", "还有", "含糊其辞", "含有", "行政", "好时", "合法", "合法权益", "合计", "合理", "合适", "合同约定", "和平", "后期", "后再", "缓慢", "回答", "或者", "及其", "即可", "即使", "计算", "继续", "加倍支付", "简易", "件", "交纳", "焦点", "结果", "解释", "今后", "仅仅", "尽快", "进度", "进行", "进行鉴定", "进一步", "近期", "经过", "纠纷", "就该", "就跟", "就是", "具体", "具体金额", "具有", "决定", "绝对", "均为", "均无", "开始", "开庭", "开庭审理", "可不", "可能", "可能性", "可是", "可以", "来自", "理应", "理由", "立案", "立即", "例如", "另外", "留下", "履行", "没有", "没有异议", "每天", "弥补", "面向", "描述", "民事", "民事裁定", "民事判决", "民事诉讼", "明确表示", "目前", "那些", "难以", "内容", "能否", "能够", "判决", "判决生效", "判决书", "判令", "普通", "期间", "期限", "其他", "其他人", "其余", "其中", "起诉", "起诉人", "起诉之日起", "前来", "前往", "清楚", "请求", "权利", "全部", "确定", "然后", "人和", "人民法院", "人民陪审员", "人数", "人中", "认定", "认可",
			"认为", "任何", "仍未", "日", "日起", "日向", "日止", "如果", "如何", "如下", "上述", "上诉", "上诉案件受理费", "上诉理由", "上诉人", "上诉状", "尚未", "涉案", "涉及", "申请", "申请人", "什么时候", "审理", "审理过程", "审判人员", "审判员", "审判长", "甚至", "时候", "时间", "实际", "始终", "事件", "事情", "事实", "是否", "适用", "适用简易程序", "首先", "书记员", "书面", "属实", "属于", "双方", "顺利", "司法鉴定", "讼争", "送达之日", "诉讼", "诉讼费", "诉讼费用", "诉讼请求", "诉讼中", "诉讼主张", "诉至", "诉状", "所有", "他们", "他人", "特别", "提出", "提供", "提交", "提起诉讼", "提前", "庭审", "通过", "同时", "突然", "万元", "为了", "为限", "为由", "为准", "未到庭参加诉讼", "未果", "未尽", "未能", "未提供证据", "未予", "位于", "问题", "无关", "无需", "无正当理由", "无正当理由拒不到庭", "希望", "下来", "下去", "现在", "限于", "相互", "相应", "详细", "享有", "向法院起诉", "向下", "项下", "形式", "形式上", "要求", "也不", "也不可能", "也是", "也有", "一案", "一笔", "一定", "一定影响", "一段", "一方", "一份", "一行", "一级", "一批", "一切", "一切损失", "一审", "一事", "一同",
			"一项", "一些", "一致", "依法", "依法改判", "依据", "依据不足", "依约", "依照", "已经", "以此", "以后", "以及", "以其", "以上", "以下", "以下简称", "以致", "义务", "因此", "因为", "引发纠纷", "应当", "应该", "用以", "由此", "由于", "有权", "有人", "有些", "于是", "与其", "与事实不符", "予以确认", "预缴", "原告", "原告与被告", "原审", "原审法院", "约定", "再次", "在此", "在前", "造成", "长", "这次", "这个", "这些", "这种", "正在", "证据", "证明", "之后", "之间", "之内", "之前", "只是", "只要", "只有", "直接", "指定", "至今", "质证", "中华人民共和国民事诉讼法", "中将", "终审判决", "终止", "主张", "自己", "组成合议庭", "左侧", "左右", "作出判决", "阿", "哎", "哎呀", "哎哟", "唉", "俺", "俺们", "按", "吧", "吧哒", "把", "罢了", "被", "本", "本着", "比", "比方", "比如", "鄙人", "彼", "彼此", "边", "别", "别的", "别说", "并", "不比", "不单", "不但", "不独", "不管", "不光", "不过", "不仅", "不拘", "不论", "不怕", "不然", "不特", "不惟", "不问", "朝", "朝着", "趁", "趁着", "乘", "冲", "除", "除此之外", "除非", "此", "此间", "此外", "从", "打", "待",
			"但", "当", "当着", "到", "得", "的", "的话", "等", "等等", "地", "第", "叮咚", "对", "多", "多少", "而", "而况", "而外", "而言", "而已", "尔后", "反过来", "反过来说", "反之", "非但", "非徒", "嘎", "嘎登", "该", "赶", "个", "各", "各个", "各位", "给", "跟", "故", "故此", "固然", "关于", "管", "归", "果然", "果真", "过", "哈", "哈哈", "呵", "和", "何", "何处", "何况", "何时", "嘿", "哼", "哼唷", "呼哧", "乎", "哗", "换句话说", "换言之", "或", "或是", "极了", "及", "及至", "即", "即便", "即或", "即令", "即若", "几", "几时", "己", "既", "既然", "既是", "继而", "加之", "假如", "假若", "假使", "鉴于", "将", "较", "较之", "叫", "接着", "借", "紧接着", "进而", "尽", "尽管", "经", "就", "就是说", "据", "具体地说", "具体说来", "开外", "靠", "咳", "可", "可见", "况且", "啦", "来", "来着", "离", "哩", "连", "连同", "两者", "了", "临", "另", "另一方面", "论", "嘛", "吗", "慢说", "漫说", "冒", "么", "每", "每当", "们", "莫若", "某", "某个", "某些", "拿", "哪", "哪边", "哪儿", "哪个", "哪里", "哪年", "哪怕", "哪天",
			"哪些", "哪样", "那", "那边", "那儿", "那个", "那会儿", "那里", "那么", "那么些", "那么样", "那时", "那样", "乃", "乃至", "呢", "能", "你", "你们", "您", "宁", "宁可", "宁肯", "宁愿", "哦", "呕", "啪达", "旁人", "呸", "凭", "凭借", "其", "其次", "其二", "其它", "其一", "起", "起见", "岂但", "恰恰相反", "前后", "前者", "且", "然而", "然则", "让", "人家", "任", "任凭", "如", "如此", "如其", "如若", "如上所述", "若", "若非", "若是", "啥", "上下", "尚且", "设若", "设使", "甚而", "甚么", "省得", "什么", "什么样", "使得", "是", "是的", "谁", "谁知", "顺", "顺着", "似的", "虽", "虽然", "虽说", "虽则", "随", "随着", "所", "所以", "他", "它", "它们", "她", "她们", "倘", "倘或", "倘然", "倘若", "倘使", "腾", "替", "同", "哇", "万一", "往", "望", "为", "为何", "为什么", "为着", "喂", "嗡嗡", "我", "我们", "呜", "呜呼", "乌乎", "无论", "无宁", "毋宁", "嘻", "吓", "相对而言", "像", "向", "向着", "嘘", "呀", "焉", "沿", "沿着", "要", "要不", "要不然", "要不是", "要么", "要是", "也", "也罢", "也好", "一", "一般", "一旦",
			"一方面", "一来", "一样", "一则", "依", "矣", "以", "以便", "以免", "以至", "以至于", "抑或", "因", "因而", "哟", "用", "由", "由此可见", "有", "有的", "有关", "又", "于", "于是乎", "与", "与此同时", "与否", "越是", "云云", "哉", "再说", "再者", "在", "在下", "咱", "咱们", "则", "怎", "怎么", "怎么办", "怎么样", "怎样", "咋", "照", "照着", "者", "这", "这边", "这儿", "这会儿", "这就是说", "这里", "这么", "这么点儿", "这么些", "这么样", "这时", "这样", "正如", "吱", "之", "之类", "之所以", "之一", "只限", "至", "至于", "诸位", "着", "着呢", "自", "自从", "自个儿", "自各儿", "自家", "自身", "综上所述", "总的来看", "总的来说", "总的说来", "总而言之", "总之", "纵", "纵令", "纵然", "纵使", "遵照", "作为", "兮", "呃", "呗", "咚", "咦", "喏", "啐", "喔唷", "嗬", "嗯", "嗳", "一下", "一个", "一何", "一则通过", "一时", "一次", "一片", "一番", "一直", "一起", "一转眼", "一边", "一面", "七", "三", "三天两头", "三番两次", "三番五次", "上", "上升", "上去", "上来", "上面", "下", "下列", "下面", "不", "不一", "不下", "不久", "不了", "不亦乐乎",
			"不仅仅", "不仅仅是", "不会", "不免", "不再", "不力", "不变", "不可", "不可开交", "不同", "不外", "不外乎", "不够", "不大", "不妨", "不定", "不对", "不少", "不尽", "不尽然", "不巧", "不已", "不常", "不得", "不得不", "不得了", "不得已", "不必", "不怎么", "不择手段", "不敢", "不料", "不断", "不日", "不时", "不曾", "不止一次", "不消", "不满", "不然的话", "不由得", "不知不觉", "不管怎样", "不经意", "不胜", "不能不", "不至于", "不若", "不要", "不起", "不足", "不迭", "不限", "与其说", "专门", "且不说", "且说", "严格", "严重", "个人", "个别", "中小", "中间", "丰富", "串行", "临到", "为主", "为什麽", "为止", "为此", "主要", "举凡", "举行", "乃至于", "之後", "乒", "乘势", "乘机", "乘胜", "乘虚", "乘隙", "九", "也就是说", "争取", "二", "二来", "二话不说", "二话没说", "云尔", "互", "互相", "五", "些", "交口", "亦", "产生", "亲口", "亲手", "亲眼", "亲自", "亲身", "人", "人人", "人们", "人民", "什麽", "仅", "今", "今後", "介于", "仍", "仍旧", "仍然", "从不", "从严", "从中", "从事", "从今以后", "从优", "从古到今", "从古至今", "从头", "从小", "从新", "从无到有",
			"从早到晚", "从未", "从来", "从此", "从此以后", "从速", "他是", "他的", "代替", "以为", "以前", "以外", "以後", "以故", "以期", "以来", "任务", "企图", "会", "伟大", "传", "传说", "传闻", "似乎", "但凡", "但愿", "何乐而不为", "何以", "何妨", "何尝", "何必", "何止", "何苦", "何须", "余外", "你是", "你的", "使", "使用", "依靠", "便", "便于", "促进", "保持", "倍加", "倍感", "倒不如", "倒不如说", "倒是", "借以", "借此", "偏偏", "做到", "偶尔", "偶而", "傥然", "儿", "允许", "充其极", "充其量", "充分", "先不先", "先后", "先後", "先生", "光是", "全体", "全力", "全年", "全然", "全身心", "全都", "全面", "八", "八成", "公然", "六", "共", "共总", "其后", "其实", "具体来说", "兼之", "内", "再", "再其次", "再则", "再有", "再者说", "决不", "决非", "准备", "凑巧", "凝神", "几乎", "几度", "几番", "几经", "凡", "凡是", "出", "出于", "出现", "分头", "切", "切不可", "切切", "切勿", "切莫", "则甚", "刚", "刚好", "刚巧", "刚才", "初", "别人", "别处", "别是", "别管", "到了儿", "到处", "到头", "到头来", "到底", "到目前为止", "前此", "前进", "前面", "加上", "加以",
			"加入", "加强", "动不动", "动辄", "勃然", "匆匆", "十分", "千", "千万", "千万千万", "半", "单", "单单", "单纯", "即刻", "即如", "即将", "即是说", "却", "却不", "历", "原来", "去", "又及", "及时", "反之亦然", "反之则", "反倒", "反倒是", "反应", "反手", "反映", "反而", "取得", "取道", "受到", "变成", "古来", "另一个", "另悉", "另方面", "另行", "只", "只当", "只怕", "只消", "叫做", "召开", "叮当", "可好", "各人", "各地", "各式", "各级", "同一", "同样", "后", "后来", "后者", "后面", "向使", "呆呆地", "呐", "呵呵", "呼啦", "咧", "哗啦", "唯有", "啊", "啊呀", "啊哈", "啊哟", "啷当", "喀", "喽", "嗡", "嘎嘎", "嘿嘿", "四", "因了", "因着", "固", "在于", "均", "坚决", "坚持", "基于", "基本", "基本上", "处在", "处处", "处理", "复杂", "多么", "多亏", "多多", "多多少少", "多多益善", "多年前", "多年来", "多数", "多次", "够瞧的", "大", "大不了", "大举", "大事", "大体", "大体上", "大凡", "大力", "大多", "大多数", "大大", "大家", "大张旗鼓", "大批", "大抵", "大概", "大略", "大约", "大致", "大都", "大量", "大面儿上", "失去", "奇", "奈", "奋勇", "她是",
			"她的", "好", "好在", "好的", "好象", "如上", "如今", "如前所述", "如同", "如常", "如是", "如次", "如此等等", "始而", "姑且", "存心", "孰料", "孰知", "它们的", "它是", "它的", "安全", "完全", "完成", "定", "实现", "宣布", "容易", "密切", "对应", "对待", "对比", "将才", "将要", "将近", "小", "少数", "尔", "尔尔", "尔等", "尤其", "就地", "就是了", "就此", "就算", "就要", "尽可能", "尽如人意", "尽心尽力", "尽心竭力", "尽早", "尽然", "尽管如此", "尽量", "局外", "居然", "届时", "屡", "屡屡", "屡次", "屡次三番", "岂", "岂止", "岂非", "川流不息", "巨大", "巩固", "差一点", "差不多", "已", "已矣", "巴", "巴巴", "带", "帮助", "常", "常常", "常言说", "常言说得好", "常言道", "平素", "年复一年", "并不是", "并排", "并没", "并没有", "并肩", "广大", "广泛", "应用", "庶乎", "庶几", "开展", "引起", "弗", "弹指之间", "强烈", "强调", "归根到底", "归根结底", "归齐", "当下", "当中", "当儿", "当前", "当即", "当口儿", "当地", "当头", "当庭", "当然", "当真", "形成", "彻夜", "彻底", "彼时", "往往", "待到", "很", "很多", "很少", "後来", "後面", "得了", "得出", "得天独厚", "得起",
			"心里", "必", "必定", "必将", "必然", "必要", "必须", "快", "快要", "忽地", "忽然", "怎奈", "怎麽", "怕", "急匆匆", "怪", "怪不得", "总是", "总结", "恍然", "恐怕", "恰似", "恰好", "恰如", "恰巧", "恰恰", "恰逢", "您们", "您是", "惟其", "惯常", "意思", "愤然", "愿意", "成为", "成年累月", "成心", "我是", "我的", "或则", "或多或少", "或曰", "或许", "战斗", "截然", "截至", "所在", "所幸", "所谓", "才", "才能", "扑通", "打从", "扩大", "抽冷子", "拦腰", "按理", "按说", "挨个", "挨家挨户", "挨次", "挨着", "挨门挨户", "挨门逐户", "据实", "据悉", "据我所知", "据此", "据称", "据说", "掌握", "接下来", "接著", "接连不断", "放量", "故而", "敞开儿", "敢", "敢于", "敢情", "数/", "整个", "断然", "方", "方便", "方才", "方能", "方面", "无", "无法", "既往", "日复一日", "日渐", "日益", "日臻", "日见", "昂然", "明显", "明确", "是不是", "是以", "显然", "显著", "普遍", "暗中", "暗地里", "暗自", "更", "更为", "更进一步", "曾", "曾经", "替代", "最", "最后", "最大", "最好", "最後", "最近", "最高", "有利", "有力", "有及", "有所", "有时", "有点", "有的是", "有着", "有著",
			"末##末", "本地", "本身", "权时", "来不及", "来得及", "来看", "来讲", "来说", "极", "极为", "极其", "极力", "极大", "极度", "极端", "某某", "根本", "格外", "梆", "概", "次第", "欢迎", "欤", "正值", "正巧", "正常", "正是", "此中", "此后", "此地", "此处", "此次", "殆", "每个", "每时每刻", "每每", "每逢", "比及", "比如说", "比照", "比起", "比较", "毕竟", "毫不", "毫无", "毫无例外", "毫无保留地", "汝", "沙沙", "没", "没奈何", "注意", "活", "深入", "满", "满足", "然", "然後", "牢牢", "特别是", "特殊", "特点", "犹且", "犹自", "独", "独自", "猛然", "猛然间", "率尔", "率然", "现代", "理当", "理该", "瑟瑟", "甚且", "甚或", "甚至于", "用来", "甫", "甭", "由是", "略", "略为", "略加", "略微", "白", "白白", "的确", "皆可", "直到", "相信", "相反", "相对", "相当", "看", "看上去", "看出", "看到", "看来", "看样子", "看看", "看见", "看起来", "真是", "真正", "眨眼", "矣乎", "矣哉", "知道", "砰", "碰巧", "社会主义", "种", "积极", "移动", "究竟", "穷年累月", "突出", "窃", "立", "立刻", "立地", "立时", "立马", "竟", "竟然", "竟而", "第二", "等到", "策略地",
			"简直", "简而言之", "简言之", "类如", "粗", "精光", "累年", "纯", "纯粹", "练习", "组成", "经常", "结合", "绝", "绝不", "绝非", "绝顶", "继之", "继后", "维持", "缕缕", "老", "老大", "老是", "老老实实", "考虑", "而又", "而后", "而论", "联系", "联袂", "背地里", "背靠背", "自后", "自打", "臭", "至若", "致", "般的", "良好", "若夫", "若果", "莫", "莫不", "莫不然", "莫如", "莫非", "获得", "藉以", "蛮", "行为", "行动", "表明", "表示", "见", "觉得", "譬喻", "譬如", "认真", "认识", "许多", "论说", "设或", "诚如", "诚然", "话说", "该当", "说来", "说说", "请勿", "诸", "诸如", "谁人", "谁料", "谨", "豁然", "贼死", "赖以", "赶快", "赶早不赶晚", "起先", "起初", "起头", "起来", "起首", "趁便", "趁势", "趁早", "趁机", "趁热", "距", "路经", "转动", "转变", "转贴", "轰然", "较为", "较比", "达到", "达旦", "迄", "迅速", "过于", "过去", "过来", "运用", "近", "近几年来", "近年来", "近来", "还", "还要", "这一来", "这点", "这般", "这麽", "进入", "进去", "进来", "进步", "连声", "连日", "连日来", "连袂", "连连", "迟早", "迫于", "适应", "适当", "逐步", "逐渐",
			"通常", "逢", "遇到", "遭到", "遵循", "避免", "那末", "那般", "那麽", "都", "采取", "里面", "重大", "重新", "重要", "针对", "长期以来", "长此下去", "长线", "长话短说", "间或", "防止", "附近", "陈年", "限制", "陡然", "除却", "除去", "除外", "除开", "除此", "除此以外", "除此而外", "随后", "随时", "随著", "隔夜", "隔日", "难得", "难怪", "难说", "难道", "难道说", "集中", "零", "需要", "非得", "非特", "非独", "顶多", "顷", "顷刻", "顷刻之间", "顷刻间", "顿时", "颇", "风雨无阻", "饱", "马上", "高低", "高兴", "默然", "默默地", "齐", "万", "一一", "数", "包括", "不错", "间接", "值得", "第三", "第四", "第五", "听到", "未尝", "写到", "读到", "一流", "二流", "三流", "只能", "采用", "那种", "之中", "也许", "传来", "分明", "不过如此", "一跃", "实际上", "一点", "两点", "三点", "历来", "善于", "一部分", "大部分", "消失", "支持", "生成", "减少", "增多", "结构", "综合", "方法", "关系", "组合", "作用", "正确", "本文", "基础", "改变", "变化", "目的", "发现", "带来", "影响", "研究", "工作", "分析", "出处", "主义", "学报", "十", "壹", "贰", "叁", "肆", "伍",
			"陆", "柒", "捌", "玖", "拾", "佰", "仟", "打开天窗说亮话", "名下", "条款", "前期", "还未", "编号", "维护", "用于", "相关", "都是", "理解", "很大", "较大", "较多", "较少", "裁定书", "人称", "完毕", "总计", "应对", "每日", "公元", "给予", "确保", "查实", "几个", "甲", "乙", "丙", "丁", "戊", "甲方", "乙方", "甲乙双方", "申请方", "责令", "一人", "两人", "有效", "符合", "只用", "想要", "一张", "原告认为", "整整", "原告的诉讼请求", "证据不足", "举证不能", "物权法", "只需", "详见", "又说", "都会", "反诉", "受理", "委托代理人", "终结", "管辖权异议", "裁定", "移送", "程序", "提起", "代表", "缴纳", "撤回", "诉称", "辩称" };

	private static Logger logger = LogManager.getLogger();

	public static JSONObject BaiDuNlp(String text) {
		AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);
		JSONObject res = client.lexer(text, null);
		return res;
	}

	public static JSONObject BaiDuNlpCustom(String text) throws UnsupportedEncodingException {
		// 传入可选参数调用接口
		HashMap<String, Object> options = new HashMap<String, Object>();
		AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);
		JSONObject res = client.lexerCustom(text, options);
		return res;
	}

	/**
	 * 内容放入百度接口分词， 结果按次数排序选自己词库种的词，用&&分隔
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static String BaiduParticiple(String content) throws UnsupportedEncodingException {
		content = content.replace("碶", "");
		Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
		Matcher m1 = CRLF.matcher(content);
		content = m1.replaceAll("");

		// String
		// regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		// Pattern p = Pattern.compile(regEx);
		// Matcher m = p.matcher(content);
		// content = m.replaceAll("").trim();

		content = content.replaceAll("\\s*", "");
		JSONObject jsonObj = BaiDuNlpCustom(content);
		String data = "";
		// System.out.print(jsonObj.toString());
		if (jsonObj.has("items")) {
			JSONArray results = jsonObj.getJSONArray("items");
			Map<String, NlpObj> map = new HashMap<String, NlpObj>();
			for (int j = 0; j < results.length(); j++) {
				JSONObject json = results.getJSONObject(j);
				String key = json.getString("item");
				// List<String> sList = Arrays.asList("SHUZI","FADIAN","MXING",
				// "MOXING","CPGZ","FAXIN","MSAY");
				// int index = sList.indexOf(json.getString("ne"));
				List<String> posList = Arrays.asList("u", "w", "p", "d", "c", "r", "f", "nr", "xc", "nt", "ns", "m", "q", "t");
				// List<String> sList = Arrays.asList("SHUZI","FADIAN","MXING",
				// "MOXING","CPGZ","FAXIN","MSAY");
				List<String> neList = Arrays.asList("PER", "ORG", "LOC", "TIME");
				String pos = json.getString("pos");
				String ne = json.getString("ne");
				int posIndex = posList.indexOf(pos);
				int neIndex = neList.indexOf(ne);
				if (posIndex == -1 && neIndex == -1) {
					key = key.replaceAll(" ", "");
					Boolean b = Arrays.asList(BLACK_LIST).contains(key);
					if (key != null && !"".equals(key) && !b && !isTime(key)) {
						if (map.get(key) == null) {
							NlpObj nlp = new NlpObj();
							nlp.setName(key);
							nlp.setTypeCode(getType(json.getString("ne")));
							nlp.setType(getType(json.getString("pos")));
							nlp.setSum(1);
							map.put(key, nlp);
						} else {
							NlpObj nlp = map.get(key);
							int sum = nlp.getSum() + 1;
							nlp.setSum(sum);
							map.put(key, nlp);
						}
					}
				}
			}

			map = sortMapByValue(map);
			if (map != null) {
				for (Map.Entry<String, NlpObj> entry : map.entrySet()) {
					// 如何取值
					String key = entry.getKey().toString();
					NlpObj nlp = map.get(key);
					data = data + nlp.getName() + "&&";
				}
				data = data.substring(0, data.length() - 2);
			}
		} else {
			logger.error("分词报错" + jsonObj.toString());
		}

		return data;
	}
	
	static {
		reGetAccessToken ();
	}
	
	public static void reGetAccessToken () {
		String url = "https://aip.baidubce.com/oauth/2.0/token";
		JSONObject param = new JSONObject();
		param.put("grant_type", "client_credentials");
		param.put("client_id", API_KEY);
		param.put("client_secret", SECRET_KEY);
		String res = "";
		try {
			res = HttpClientUtil.doPost(url, param.toString());
		} catch (Exception e) {
			ACCESS_TOKEN = null;
			logger.error("获取accesstoken错误");
		}
		if(res != null) {
			JSONObject obj = new JSONObject(res);
			if(obj.has("access_token")) {
				ACCESS_TOKEN = obj.getString("access_token");
			}
		}
	}
	
	public static String getAccessToken () {
		if(ACCESS_TOKEN == null) {		
			reGetAccessToken();
		}
		return ACCESS_TOKEN;
	}
	
	public static JSONObject BaiDuNlpCustomApi(String text) {
		// 传入可选参数调用接口
		String url = "https://aip.baidubce.com/rpc/2.0/nlp/v1/lexer_custom?access_token=" + getAccessToken ();
		JSONObject param = new JSONObject();
		param.put("text", text);
		String res = HttpClientUtil.doPost(url, param.toString());
		if(res != null) {
			return new JSONObject(res);
		} else {
			return null;
		}
	}
	
	/**
	 * 内容放入百度接口分词， 结果按次数排序选自己词库种的词，用&&分隔
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static String BaiduParticipleApi(String content) {
		content = content.replace("碶", "");
		Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
		Matcher m1 = CRLF.matcher(content);
		content = m1.replaceAll("");

		// String
		// regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		// Pattern p = Pattern.compile(regEx);
		// Matcher m = p.matcher(content);
		// content = m.replaceAll("").trim();

		content = content.replaceAll("\\s*", "");
		JSONObject jsonObj = BaiDuNlpCustomApi(content);
		String data = "";
		// System.out.print(jsonObj.toString());
		if (jsonObj.has("items")) {
			JSONArray results = jsonObj.getJSONArray("items");
			Map<String, NlpObj> map = new HashMap<String, NlpObj>();
			for (int j = 0; j < results.length(); j++) {
				JSONObject json = results.getJSONObject(j);
				String key = json.getString("item");
				// List<String> sList = Arrays.asList("SHUZI","FADIAN","MXING",
				// "MOXING","CPGZ","FAXIN","MSAY");
				// int index = sList.indexOf(json.getString("ne"));
				List<String> posList = Arrays.asList("u", "w", "p", "d", "c", "r", "f", "nr", "xc", "nt", "ns", "m", "q", "t");
				// List<String> sList = Arrays.asList("SHUZI","FADIAN","MXING",
				// "MOXING","CPGZ","FAXIN","MSAY");
				List<String> neList = Arrays.asList("PER", "ORG", "LOC", "TIME");
				String pos = json.getString("pos");
				String ne = json.getString("ne");
				int posIndex = posList.indexOf(pos);
				int neIndex = neList.indexOf(ne);
				if (posIndex == -1 && neIndex == -1) {
					key = key.replaceAll(" ", "");
					Boolean b = Arrays.asList(BLACK_LIST).contains(key);
					if (key != null && !"".equals(key) && !b && !isTime(key)) {
						if (map.get(key) == null) {
							NlpObj nlp = new NlpObj();
							nlp.setName(key);
							nlp.setTypeCode(getType(json.getString("ne")));
							nlp.setType(getType(json.getString("pos")));
							nlp.setSum(1);
							map.put(key, nlp);
						} else {
							NlpObj nlp = map.get(key);
							int sum = nlp.getSum() + 1;
							nlp.setSum(sum);
							map.put(key, nlp);
						}
					}
				}
			}

			map = sortMapByValue(map);
			if (map != null) {
				for (Map.Entry<String, NlpObj> entry : map.entrySet()) {
					// 如何取值
					String key = entry.getKey().toString();
					NlpObj nlp = map.get(key);
					data = data + nlp.getName() + "&&";
				}
				data = data.substring(0, data.length() - 2);
			}
		} else {
			logger.error("分词报错" + jsonObj.toString());
		}

		return data;
	}

	private static String getType(String code) {
		String result = null;
		switch (code) {
		case "Ag":
			result = "形语词";
			break;
		case "a":
			result = "形容词";
			break;
		case "ad":
			result = "副形词";
			break;
		case "an":
			result = "名形词";
			break;
		case "b":
			result = "区别词";
			break;
		case "c":
			result = "连词";
			break;
		case "d":
			result = "副词";
			break;
		case "e":
			result = "叹词";
			break;
		case "f":
			result = "方位名词";
			break;
		case "g":
			result = "语素";
			break;
		case "h":
			result = "前接成分";
			break;
		case "i":
			result = "成语";
			break;
		case "j":
			result = "简称略语";
			break;
		case "k":
			result = "后接成分";
			break;
		case "l":
			result = "习惯语";
			break;
		case "m":
			result = "数量词";
			break;
		case "n":
			result = "名词";
			break;
		case "nr":
			result = "人名";
			break;
		case "ns":
			result = "地名";
			break;
		case "nt":
			result = "机构团体名";
			break;
		case "nw":
			result = "作品名";
			break;
		case "nz":
			result = "其他专名";
			break;
		case "o":
			result = "拟声词";
			break;
		case "p":
			result = "介词";
			break;
		case "q":
			result = "量词";
			break;
		case "r":
			result = "代词";
			break;
		case "s":
			result = "处所词";
			break;
		case "t":
			result = "时间词";
			break;
		case "tg":
			result = "时语素";
			break;
		case "u":
			result = "助词";
			break;
		case "v":
			result = "动词";
			break;
		case "vg":
			result = "动语素";
			break;
		case "vd":
			result = "东副词";
			break;
		case "vn":
			result = "动名词";
			break;
		case "w":
			result = "标点符号";
			break;
		case "x":
			result = "非语素字";
			break;
		case "xc":
			result = "其他虚词";
			break;
		case "y":
			result = "语气词";
			break;
		case "z":
			result = "状态词";
			break;
		// 特殊
		case "PER":
			result = "人名";
			break;
		case "LOC":
			result = "地名";
			break;
		case "ORG":
			result = "机构名";
			break;
		case "TIME":
			result = "时间";
			break;

		// 自定义词库
		case "FADIAN":
			result = "法典";
			break;
		case "MXING":
			result = "模型超15";
			break;
		case "MOXING":
			result = "模型";
			break;
		case "CPGZ":
			result = "裁判规则";
			break;
		case "FAXIN":
			result = "法信词库";
			break;
		case "MSAY":
			result = "民事案由";
			break;
		default:
			result = "XXX";
		}
		return result;
	}

	/**
	 * 使用 Map按value进行排序
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, NlpObj> sortMapByValue(Map<String, NlpObj> oriMap) {
		if (oriMap == null || oriMap.isEmpty()) {
			return null;
		}
		Map<String, NlpObj> sortedMap = new LinkedHashMap<String, NlpObj>();
		List<Map.Entry<String, NlpObj>> entryList = new ArrayList<Map.Entry<String, NlpObj>>(oriMap.entrySet());
		Collections.sort(entryList, new MapValueComparator());

		Iterator<Map.Entry<String, NlpObj>> iter = entryList.iterator();
		Map.Entry<String, NlpObj> tmpEntry = null;
		while (iter.hasNext()) {
			tmpEntry = iter.next();
			sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
		}
		return sortedMap;
	}

	public static Boolean isTime(String str) {
		String reg = "\\d{4}年\\d{1,2}月\\d{1,2}日.*";
		String reg1 = "\\d{4}年\\d{1,2}月.*";
		String reg2 = "\\d{1,2}月\\d{1,2}日.*";
		boolean isTime = Pattern.matches(reg, str);
		boolean isTime1 = Pattern.matches(reg1, str);
		boolean isTime2 = Pattern.matches(reg2, str);
		if (isTime || isTime1 || isTime2) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * 内容放入百度接口分词， 结果按次数排序选自己词库种的词，用&&分隔
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static String BaiduParticiple(String content, String separator ) throws UnsupportedEncodingException {
		content = content.replace("碶", "");
		Pattern CRLF = Pattern.compile("(\r\n|\r|\n|\n\r)");
		Matcher m1 = CRLF.matcher(content);
		content = m1.replaceAll("");

		// String
		// regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		// Pattern p = Pattern.compile(regEx);
		// Matcher m = p.matcher(content);
		// content = m.replaceAll("").trim();

		content = content.replaceAll("\\s*", "");
		JSONObject jsonObj = BaiDuNlpCustom(content);
		String data = "";
		// System.out.print(jsonObj.toString());
		if (jsonObj.has("items")) {
			JSONArray results = jsonObj.getJSONArray("items");
			Map<String, NlpObj> map = new HashMap<String, NlpObj>();
			for (int j = 0; j < results.length(); j++) {
				JSONObject json = results.getJSONObject(j);
				String key = json.getString("item");
				// List<String> sList = Arrays.asList("SHUZI","FADIAN","MXING",
				// "MOXING","CPGZ","FAXIN","MSAY");
				// int index = sList.indexOf(json.getString("ne"));
				List<String> posList = Arrays.asList("u", "w", "p", "d", "c", "r", "f", "nr", "xc", "nt", "ns", "m", "q", "t");
				// List<String> sList = Arrays.asList("SHUZI","FADIAN","MXING",
				// "MOXING","CPGZ","FAXIN","MSAY");
				List<String> neList = Arrays.asList("PER", "ORG", "LOC", "TIME");
				String pos = json.getString("pos");
				String ne = json.getString("ne");
				int posIndex = posList.indexOf(pos);
				int neIndex = neList.indexOf(ne);
				if (posIndex == -1 && neIndex == -1) {
					key = key.replaceAll(" ", "");
					Boolean b = Arrays.asList(BLACK_LIST).contains(key);
					if (key != null && !"".equals(key) && !b && !isTime(key)) {
						if (map.get(key) == null) {
							NlpObj nlp = new NlpObj();
							nlp.setName(key);
							nlp.setTypeCode(getType(json.getString("ne")));
							nlp.setType(getType(json.getString("pos")));
							nlp.setSum(1);
							map.put(key, nlp);
						} else {
							NlpObj nlp = map.get(key);
							int sum = nlp.getSum() + 1;
							nlp.setSum(sum);
							map.put(key, nlp);
						}
					}
				}
			}

			map = sortMapByValue(map);
			if (map != null) {
				for (Map.Entry<String, NlpObj> entry : map.entrySet()) {
					// 如何取值
					String key = entry.getKey().toString();
					NlpObj nlp = map.get(key);
					data = data + nlp.getName() + separator;
				}
				data = data.substring(0, data.length() - separator.length());
			}
		} else {
			logger.error("分词报错" + jsonObj.toString());
		}

		return data;
	}
}

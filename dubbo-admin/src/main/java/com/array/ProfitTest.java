package com.array;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO 类的描述
 *
 * @author liuyanli
 * @createTime 2018-03-10 00:17:41
 */
public class ProfitTest {

    public static void main(String[] args) {
        ProfitTest test = new ProfitTest();
        test.getProfit("7");
        test.getProfit("30");
    }
    /**
     * 获取万份收益和七日年化收益数据
     *
     * @param days
     * @return com.tuandai.ms.wallet.dtos.ResponseDTO<com.tuandai.ms.wallet.dtos.ProfitJsonDTO>
     * @author liuyanli
     * @createTime 2018-03-09 23:06:04
     */
    public ResponseDTO<ProfitJsonDTO> getProfit(String days) {
//        String txnId = SystemContext.getTrackUUid();
//        logger.info("[获取万份收益和七日年化收益数据]-txnId:[{}], 请求参数如下:[{}]", txnId, days);

        ResponseDTO<ProfitJsonDTO> dto = new ResponseDTO<ProfitJsonDTO>();
        ProfitJsonDTO profitJsonDTO = new ProfitJsonDTO();
        Qrnhsy qrnhsy = new Qrnhsy();
        Wfsy wfsy = new Wfsy();


        String wfsyStr = HttpUtils.doPost("http://116.228.64.55:28082/FundDataApi/Chart/78880000/000709/json/wfsy.json");
        System.out.println(wfsyStr);

        String regex="\"label\":(.*?),\"color\"";
        Matcher matcher= Pattern.compile(regex).matcher(wfsyStr);
        while(matcher.find())
        {
            String ret=matcher.group(1);
            System.out.println(ret);
            wfsyStr=wfsyStr.replaceAll(ret, "1");
        }
        wfsyStr = wfsyStr.split("=")[1];
        System.out.println(wfsyStr);


        JSONObject allJson = JSON.parseObject(wfsyStr);



        JSONObject wfsyJson = allJson.getJSONObject("wfsy");
        String[][] wfsyA = readTwoDimensionData2(wfsyJson, "data");
        String[][] wfsyDaysArray = new String[Integer.valueOf(days)][];
        for (int i = wfsyA.length - Integer.valueOf(days); i < wfsyA.length; i++) {
            wfsyDaysArray[Integer.valueOf(days) + i - wfsyA.length] = wfsyA[i];
        }
        wfsy.setData(wfsyDaysArray);
        profitJsonDTO.setWfsy(wfsy);


        JSONObject qrnhsyJson = allJson.getJSONObject("qrnhsy");
        String[][] qrnhsyA = readTwoDimensionData2(qrnhsyJson, "data");

        String[][] qrnhsyDaysArray = new String[Integer.valueOf(days)][];
        for (int i = qrnhsyA.length - Integer.valueOf(days); i < qrnhsyA.length; i++) {
            qrnhsyDaysArray[Integer.valueOf(days) + i - qrnhsyA.length] = qrnhsyA[i];
        }
        qrnhsy.setData(qrnhsyDaysArray);
        profitJsonDTO.setQrnhsy(qrnhsy);

        dto.setData(profitJsonDTO);

        System.out.println(dto.toString());
        System.out.println(profitJsonDTO.toString());
        return dto;


    }


     //从json中, 读取属性名为str的数据, 到二维数组Data中
    public String[][] readTwoDimensionData2(JSONObject json, String str)
    {
        String[][] Data = null;

                JSONArray Array1 = json.getJSONArray(str);          //获取属性名对应的二维数组

                Data = new String[Array1.size()][];
                for(int i = 0; i < Array1.size(); i ++)
                {
                    JSONArray Array2 = Array1.getJSONArray(i);      //获取一维数组

                    Data[i] = new String[Array2.size()];
                    for(int j = 0; j < Array2.size(); j ++) {
                        Data[i][j] = Array2.getString(j);           //获取一维数组中的数据
                    }
                }



        return Data;
    }


}

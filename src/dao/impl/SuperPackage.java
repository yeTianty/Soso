package dao.impl;

import dao.CallService;
import dao.NetService;
import dao.SendService;
import entity.Common;
import entity.MobileCard;
import entity.ServicePackage;

public class SuperPackage extends ServicePackage implements
        CallService, SendService, NetService {
    private int talkTime;
    private int smsCount;
    private int flow;

    public SuperPackage() {
        this.talkTime = 200;
        this.flow = 1024 * 1;
        this.smsCount = 50;
        this.price = 78;
    }

    public int getTalkTime() {
        return talkTime;
    }

    public void setTalkTime(int talkTime) {
        this.talkTime = talkTime;
    }

    public int getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(int smsCount) {
        this.smsCount = smsCount;
    }

    public int getFlow() {
        return flow;
    }

    public void setFlow(int flow) {
        this.flow = flow;
    }

    @Override
    public int call(int minCount, MobileCard card) throws Exception {
        int temp = minCount;
        for (int i = 0; i < minCount; i++) {
            if (this.talkTime - card.getRealTalkTime() >= 1) {
                card.setRealTalkTime(card.getRealTalkTime() + 1);
            } else if (card.getMoney() >= 0.2) {
                card.setRealTalkTime(card.getRealTalkTime() + 1);
                card.setMoney(Common.sub(card.getMoney(), 0.2));
                card.setConsumAmount(card.getConsumAmount() + 0.2);
            } else {
                temp = i;
                throw new Exception("本次已通话" + temp + "分钟,您的余额不足，请充值后再使用！");
            }
        }
        return temp;
    }

    @Override
    public int netPlay(int flow, MobileCard card) throws Exception {
        int temp = flow;
        for (int i = 0; i < flow; i++) {
            if (this.flow - card.getRealFlow() >= 1) {
                card.setRealFlow(card.getRealFlow() + 1);
            } else if (card.getMoney() >= 0.1) {
                card.setRealFlow(card.getRealFlow() + 1);
                card.setMoney(Common.sub(card.getMoney(), 0.1));
                card.setConsumAmount(card.getConsumAmount() + 0.1);
            } else {
                temp = i;
                throw new Exception("本次已使用流量" + temp + "MB,您的余额不足，请充值后再使用！");
            }
        }
        return temp;
    }

    @Override
    public int send(int count, MobileCard card) throws Exception {
        int temp = count;
        for (int i = 0; i < count; i++) {
            if (this.smsCount - card.getRealSMSCount() >= 1) {
                card.setRealSMSCount(card.getRealSMSCount() + 1);
            } else if (card.getMoney() > 0.1) {
                card.setRealSMSCount(card.getRealSMSCount() + 1);
                card.setMoney(Common.sub(card.getMoney(), 0.1));
                card.setConsumAmount(card.getConsumAmount() + 0.1);
            } else {
                temp = i;
                throw new Exception("本次已发送" + temp + "条，您的余额不足，请充值后再使用");
            }
        }
        return temp;
    }

    @Override
    public void showInfo() {
        System.out.println("超人套餐：通话时长为" + this.talkTime + "分钟/月，短信条数为" +
                this.smsCount + "条/月，上网流量为" + this.flow / 1024 + "GB/月。");
    }
}

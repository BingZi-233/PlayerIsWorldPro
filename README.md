# PlayerIsWorld（基于Taboolib重构）

## 宗旨

> 帮助服主更好的管理世界，在不影响其他玩家数据的情况下对单独某个玩家进行精确控制。

## 作用

> 给每一个玩家分配一个主世界<br>
>> 最大限度规避因为某个玩家崩溃世界区块而导致服务器无法正常启动
> > （或者导致所有玩家丢失生存进度），从而避免玩家流失。

## 兼容插件列表

- SlimeWorldManager

> 作用：
>> 加快世界的生成以及加载过程，测试效果可以提升大约100%~400%的速度。
> > 同时也是BC跨服同步的必须插件，借助此插件可以实现多服务器使用相同的地图。

## 计划更新功能

> 1. 指定世界加载&卸载<br>
>> 此功能主要是针对小游戏服务器，用于游戏地图的加载和管理上，避免服务器下出现大量相同的地图。
> 2. 拒绝某个指定玩家进入专属世界<br>
>> 此功能用于保护玩家世界不遭受侵害，指定的玩家将无法通过任何方式进入被拒绝的世界。（包括但不限于Teleport）
> 3. 支持普通世界快速加载<br>
>> 此功能用于快速加载普通玩家世界，当前受限于SlimeWorldManager插件功能，生成的世界均为空无一物。
> > 在后续将对其进行改正，让只使用SlimeWorldManager的服务器依旧可以享受快速加载的效果。
> 4. 对空岛插件的支持<br>
>> 此功能主要是更好的对空岛服务器的玩家空岛管理，避免多个玩家身处同一世界产生的各种问题。

## 制作者

> 1. BingZi233（冰上云梦）
>> ![BingZi233头像](https://s3.ax1x.com/2021/01/17/srWFpj.jpg)
# NIM部署OS指南  

1. 在NIM Server的机器上执行，将需要部署的机器配置到NIM SERVER的/etc/hosts  
    hostent -a ***192.168.1.39*** -h ***285AT_LPAR9***  

    删除IP，在回滚的时候使用  
    hostent -d ***192.168.1.39***  


2. 在NIM Server上定义NIM Client，(执行时间很长,需要BAO流异步执行,好几秒种)  

    查看当前的NIM Client  
    lsnim -c networks -l  

    定义NIM Client  
    nim -o define -t standalone -a platform=chrp -a if1="find_net ***285AT_LPAR9*** 0" -a cable_type1=bnc -a netboot_kernel=mp ***285AT_LPAR9***  

    删除NIM Client  
    nim -o remove ***285AT_LPAR9***  

3. 在NIM Server上面分派资源给客户机  
    须分派三种资源：mksysb、spot、bosinst。资源都需要先创建好，在nim上定义好。bosinst_5306_data为静默安装脚本。  
    查询三资源的命令  
    lsnim | grep mksysb  
    lsnim | grep spot  
    lsnim | grep bosinst_data  

    分配资源  
    nim -o allocate -a mksysb=***mksysb6100_08_02*** -a spot=***spot6100_08_02*** -a bosinst_data=***bosinst_data*** ***285AT_LPAR9***  

    删除分派关系  
    nim -o deallocate -a mksysb=***mksysb6100_08_02*** -a spot=***spot6100_08_02 ***-a bosinst_data=***bosinst_data*** ***285AT_LPAR9***  

4. 复查NIM Client的配置  
    输入以下命令查看客户机对象参数：状态Cstate应为ready for a NIM operation  
    lsnim -l ***285AT_LPAR9***  

5. 在NIM Server执行以下命令启动安装  
    nim -o bos_inst -a source=mksysb -a boot_client=no -a accept_licenses=yes ***285AT_LPAR9***  

    停止安装  
    nim -Fo reset ***285AT_LPAR9***  

6. 在HMC上执行，将IP信息设置到客户机，并且指示NIM服务开始安装。  
    lpar_netboot -f -t ent -s auto -d auto -D -S ***192.168.1.101*** -G ***192.168.1.254*** -C ***192.168.1.39***  -K ***255.255.255.0*** -m ***E28D0DE5B302***  ***285AT_LPAR9***  ***285AT_LPAR9***  ***Server-8233-E8B-SN06285AT***  

    \# Connecting to tyf2.  
    \# Connected  
    \# Checking for power off.  
    \# Power off complete.  
    \# Power on tyf2 to Open Firmware.  
    \# Power on complete.  
    \# Client IP address is 10.71.166.107.  
    \# Server IP address is 10.71.167.28.  
    \# Gateway IP address is 10.71.166.1.  
    \# Subnetmask IP address is 255.255.254.0.  
    \# Getting adapter location codes.  
    \# /vdevice/l-lan@30000002 ping successful.  
    \# Network booting install adapter.  
    \# bootp sent over network.  
    \# Network boot proceeding, lpar_netboot is exiting.  
    \# Finished.  

7. 在HMC上打开连接到客户机，可以实时查看安装过程。非安装必须步骤。  
    mkvterm -m ***Server-8233-E8B-SN06285AT*** -p ***285AT_LPAR9***  

8. 验证安装，我们通过使用ssh登陆确认安装是否成功。注意：请先确认NIM Server上的镜像是否开启SSH功能，如果镜像没有开启SSH，那么部署出来的OS也是没有开启SSH的。  
    登陆客户机，镜像中的默认登陆信息为：root/root  
    ssh ***192.168.1.39***  

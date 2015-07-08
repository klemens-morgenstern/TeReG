package tereg;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import tereg.graph.PassBar;
import tereg.graph.PassPie;
import tereg.graph.ReqPie;


public class Main {

	public static void main(String[] args) throws Exception 
	{
		Serializer serializer = new Persister();
		DetailsReportXml details = new DetailsReportXml();
		OverviewReportXml overview = new OverviewReportXml();
		
		PassPie.makeChart("test.png", 21, 10, 7);
		
		PassBar b = new PassBar();
		
		b.addBar("Deine mudda!", 5, 10, 20);
		b.addBar("opfah", 3, 5, 7);
		
		b.chartToFile("test2.png");


		
		RequirementCoverageOverview reqOvw = new RequirementCoverageOverview();
		
		serializer.read(reqOvw, new File("F:\\edspace\\TeReG\\report\\TESSY_RequirementExecutionCoverageOverview.xml"));
		
		reqOvw.writeDox("out/dings.dox");
		
		/*serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_CommInit.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_CustomerComm.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_CustomerInit.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_CustomerMain.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_CustomerRequest.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_GetAddFader.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_GetCanStatus.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_GetCommanderHcStatus.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_GetGeneralStatus.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_GetGunnerHcStatus.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_GetHcStatus.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_GetModeSettings.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_GetOverrideBit.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_GetShutdown.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_GetSynchroSwitchStatus.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_GetUartCh1Status.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_GetUartCh2Status.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_GyroMain.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_InitHandController.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_ModeSelectDacsVIEW.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_OverrideDCM.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_ResetCommunicationDevice.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_SetCpuInitValuesAx1.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustInterfaceCif_SetCpuInitValuesAx2.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustomerComm_Communicate.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_CustomerComm_Init.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_Customer_CAN_GetCommandFlags.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_Customer_CAN_GetCustomerModeDemand.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_Customer_CAN_Init.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_Customer_CAN_Main.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_Customer_CAN_SetPositionError.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_Customer_CAN_Set_Handle_Signals.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_Customer_GetModeSettings.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_Customer_GyroMain.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_Customer_Init.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_Customer_InitHandController.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_Customer_Main.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_Customer_ModeSelectDacsVIEW.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_Customer_Request.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_MathFilter_CalcContr.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_MathFilter_CalcFF.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_MathFilter_Init.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_ModeAbsPos_Calc.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_ModeAbsPos_Init.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_ModeAbsPos_Reinit.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_ModeDriftCompensation_Init.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_ModeDriftCompensation_Main.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_DetailsReport_ModeDriftCompensation_Reinit.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_CommInit.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_CustomerComm.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_CustomerInit.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_CustomerMain.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_CustomerRequest.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_GetAddFader.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_GetCanStatus.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_GetCommanderHcStatus.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_GetGeneralStatus.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_GetGunnerHcStatus.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_GetHcStatus.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_GetModeSettings.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_GetOverrideBit.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_GetShutdown.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_GetSynchroSwitchStatus.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_GetUartCh1Status.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_GetUartCh2Status.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_GyroMain.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_InitHandController.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_ModeSelectDacsVIEW.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_OverrideDCM.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_ResetCommunicationDevice.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_SetCpuInitValuesAx1.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_CustInterfaceCif_SetCpuInitValuesAx2.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MathCRC8_CalcByte.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MathCRC8_CalcByteBuffer.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MilCAN_CheckConfigModeEntryCondition.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MilCAN_CheckConfigModeSM.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MilCAN_CheckNewCanMessageForFlag.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MilCAN_GetMessage.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MilCAN_GetStatus.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MilCAN_Init.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MilCAN_InitMessage.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MilCAN_ReceiveMessage.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MilCAN_ResetDevice.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MilCAN_SendMessage.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MilCAN_SetConfigMode.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MilCAN_SetFilter.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MilCAN_SmSystemModes.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MilCAN_StubUint16.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_MilCAN_StubVoid.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_AbsPos_Calc.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_AbsPos_Calc_El.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_AbsPos_Calc_Tr.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_AbsPos_Init.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_AbsPos_Reinit.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Bit_Get_CanError.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Bit_Get_CResult.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Bit_Get_CResult_Axis1.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Bit_Get_CResult_Axis2.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Bit_Get_PResult.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Bit_Get_PResult_Axis1.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Bit_Get_PResult_Axis2.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Bit_Get_Result_Axis1.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Bit_Get_Result_Axis2.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Bit_Get_Status.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Bit_Set_CanError.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_CalcShapeHandle.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_GetModeSettings.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_GyroMain.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_Init.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_InitHandController.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_Main.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_Main_SysCon1.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_Main_SysCon1_Edge_Fres_Sv.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_Main_SysCon1_Edge_Wcsp.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_Main_SysCon2.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_ModeSelectDacsVIEW.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_ModeSelectDacsVIEW_HandleMode.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_ModeSelectDacsVIEW_HandleMode_Designate.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_ModeSelectDacsVIEW_HandleMode_Load.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_ModeSet.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_ModeSet_GetRequestedMode.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_ReadHandController.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_Request.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_RequestCascadedStab.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_RequestClassicStab.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_RequestClassicStabAxis1.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_RequestClassicStabAxis2.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_RequestPosition.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_RequestPosition_Elevation.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_RequestPosition_Traversal.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_RequestSpeed.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_RequestStandby.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Core_Test_DigitalOut.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_DriftCompensation_Init.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_DriftCompensation_Main.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_DriftCompensation_Reinit.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Filter_CalcContr.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Filter_CalcFF.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_Filter_Init.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_DoOperationalMode.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_DoPreOperationalMode.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_DoSynchronize.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_DoSynchronize_SetPTUs.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_GetCommandFlags.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_GetCustomerModeDemand.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_Init.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_Main.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_ReceiveMessage.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_ReceiveMessage_FccCommand.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_ReceiveMessage_FccError.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_ReceiveMessage_GunnerMessage.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_ReceiveMessage_GunnerMessage_Aiming.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_ReceiveMessage_GunnerMessage_Default.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_ReceiveMessage_GunnerMessage_Standby.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_ReceiveMessage_TDSSReversionaryControl.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_ResetPositionSignalTime.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_ResetSpeedSignalTime.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_CifPara_Compile_Date.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_CifPara_Compile_Time.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_CifPara_DecNumber.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_CifPara_Number.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_CifSw_Compile_Date.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_CifSw_Compile_Time.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_CifSw_DecNumber.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_CifSw_Number.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_CpuPara_Compile_Date.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_CpuPara_Compile_Time.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_CpuPara_DecNumber.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_CpuPara_Number.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_CpuSW_Compile_Date.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_CpuSW_Compile_Time.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_CpuSW_DecNumber.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_CpuSW_Number.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_PBit_Status.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_System_Counter_On.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendHUMSReportTDSS_System_Powered.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendIBIT.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SendMessage.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SetPositionError.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_SetSpeedSignalTimeout.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_Set_Handle_Signals.xml"));
		serializer.read(details, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_DetailsReport_TwinX_MilCAN_Set_ID.xml"));
		serializer.read(overview, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_OverviewReport_MathCRC8.xml"));
		serializer.read(overview, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_OverviewReport_MilCAN.xml"));
		serializer.read(overview, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_OverviewReport_TwinX_AbsPos.xml"));
		serializer.read(overview, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_OverviewReport_TwinX_Bit.xml"));
		serializer.read(overview, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_OverviewReport_TwinX_Core.xml"));
		serializer.read(overview, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_OverviewReport_TwinX_MilCAN.xml"));
		serializer.read(overview, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_OverviewReport_TwinX_MilCAN_ReceiveMessage.xml"));
		serializer.read(overview, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_OverviewReport_TwinX_MilCAN_SendHUMS.xml"));
		serializer.read(overview, new File("F:\\edspace\\TeReG\\report\\Service\\TESSY_OverviewReport_TwinX_MilCAN_SendMessage.xml"));
		serializer.read(overview, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_OverviewReport_Customer.xml"));
		serializer.read(overview, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_OverviewReport_CustomerComm.xml"));
		serializer.read(overview, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_OverviewReport_CustomerInterfaceCif.xml"));
		serializer.read(overview, new File("F:\\edspace\\TeReG\\report\\Customer\\TESSY_OverviewReport_Customer_CAN.xml"));
*/		
		serializer.read(details, new File("Details2.xml"));
				
	}

}

USE [passport_agricultural]
GO

INSERT INTO [dbo].[event_passport_table]
           ([id_passport]
           ,[id_organization]
           ,[message_event]
           ,[date_time_event]
           ,[type_event])
     VALUES
           (3,
            8,
            '��������� ���� 3 ������������ ���������',
            GETDATE(),
            '���������� ���������');
GO



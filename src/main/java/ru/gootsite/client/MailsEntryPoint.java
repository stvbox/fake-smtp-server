package ru.gootsite.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

import java.util.List;

public class MailsEntryPoint implements EntryPoint {
    private MailsServiceAsync mailsService = GWT.create(MailsService.class);

    private CellTable<MailDTO> table;
    private List<MailDTO> contactsList;

    LoadContactsCallback loadContactsCallback = new LoadContactsCallback();

    public void onModuleLoad() {
        mailsService.getMails(loadContactsCallback);

        // Create a CellTable.
        this.table = new CellTable<MailDTO>();


        TextColumn<MailDTO> dateColumn = new TextColumn<MailDTO>() {
            @Override
            public String getValue(MailDTO mail) {
                return mail.date;
            }
        };

        TextColumn<MailDTO> nameColumn = new TextColumn<MailDTO>() {
            @Override
            public String getValue(MailDTO mail) {
                return mail.subject;
            }
        };

        TextColumn<MailDTO> addressColumn = new TextColumn<MailDTO>() {
            @Override
            public String getValue(MailDTO mail) {
                return mail.body;
            }
        };

        TextColumn<MailDTO> headersColumn = new TextColumn<MailDTO>() {
            @Override
            public String getValue(MailDTO mail) {
                return mail.headers;
            }
        };

        // Add the columns.
        table.addColumn(dateColumn, "Date");
        table.addColumn(nameColumn, "Subject");
        table.addColumn(addressColumn, "Body");
        table.addColumn(headersColumn, "Headers");

        // Add it to the root panel.
        RootPanel.get().add(table);
    }

    class LoadContactsCallback implements AsyncCallback<List<MailDTO>> {

        @Override
        public void onFailure(Throwable caught) {

        }

        @Override
        public void onSuccess(List<MailDTO> result) {
            contactsList = result;

            // Set the total row count. This isn't strictly necessary, but it affects
            // paging calculations, so its good habit to keep the row count up to date.
            table.setRowCount(contactsList.size(), true);

            // Push the data into the widget.
            table.setRowData(0, contactsList);
        }
    }
}

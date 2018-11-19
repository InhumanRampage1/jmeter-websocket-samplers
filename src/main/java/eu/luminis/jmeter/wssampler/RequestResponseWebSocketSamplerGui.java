/*
 * Copyright © 2016, 2017, 2018 Peter Doornbosch
 *
 * This file is part of JMeter-WebSocket-Samplers, a JMeter add-on for load-testing WebSocket applications.
 *
 * JMeter-WebSocket-Samplers is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * JMeter-WebSocket-Samplers is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package eu.luminis.jmeter.wssampler;

import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;

import java.awt.BorderLayout;


public class RequestResponseWebSocketSamplerGui extends AbstractSamplerGui {

    private RequestResponseWebSocketSamplerGuiPanel settingsPanel;

    public RequestResponseWebSocketSamplerGui() {
        init();
    }

    private void init() {
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH);
        settingsPanel = new RequestResponseWebSocketSamplerGuiPanel();
        add(settingsPanel, BorderLayout.CENTER);
    }

    @Override
    public void clearGui() {
        super.clearGui();
        settingsPanel.clearGui();
    }

    @Override
    public String getStaticLabel() {
        return "WebSocket request-response Sampler";
    }

    @Override
    public String getLabelResource() {
        return null;
    }

    @Override
    public TestElement createTestElement() {
        RequestResponseWebSocketSampler element = new RequestResponseWebSocketSampler();
        configureTestElement(element);  // Essential because it sets some basic JMeter properties (e.g. the link between sampler and gui class)
        return element;
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);
        if (element instanceof RequestResponseWebSocketSampler) {
            RequestResponseWebSocketSampler sampler = (RequestResponseWebSocketSampler) element;
            settingsPanel.setCreateNewConnection(sampler.getCreateNewConnection());
            settingsPanel.setTLS(sampler.getTLS());
            settingsPanel.serverField.setText(sampler.getServer());
            settingsPanel.portField.setText(sampler.getPort());
            settingsPanel.pathField.setText(sampler.getPath());
            settingsPanel.enableConnectionIdOption((WebsocketSampler.multipleConnectionsEnabled));
            settingsPanel.connectionIdField.setText(sampler.getConnectionId());
            settingsPanel.connectionTimeoutField.setText(sampler.getConnectTimeout());
            settingsPanel.setType(sampler.getBinary()? DataPayloadType.Binary: DataPayloadType.Text);
            settingsPanel.setRequestData(sampler.getRequestData());
            settingsPanel.readTimeoutField.setText(sampler.getReadTimeout());
            settingsPanel.setReadDataFromFile(sampler.getLoadDataFromFile());
            settingsPanel.setDataFile(sampler.getDataFile());
        }
    }

    @Override
    public void modifyTestElement(TestElement element) {
        configureTestElement(element);
        if (element instanceof RequestResponseWebSocketSampler) {
            RequestResponseWebSocketSampler sampler = (RequestResponseWebSocketSampler) element;
            sampler.setTLS(settingsPanel.getTLS());
            sampler.setServer(settingsPanel.serverField.getText());
            sampler.setPort(settingsPanel.portField.getText());
            sampler.setPath(settingsPanel.pathField.getText());
            sampler.setConnectionId(settingsPanel.connectionIdField.getText());
            sampler.setConnectTimeout(settingsPanel.connectionTimeoutField.getText());
            sampler.setBinary(settingsPanel.getType().equals(DataPayloadType.Binary));
            sampler.setRequestData(settingsPanel.getRequestData());
            sampler.setCreateNewConnection(settingsPanel.newConnection.isSelected());
            sampler.setReadTimeout(settingsPanel.readTimeoutField.getText());
            sampler.setLoadDataFromFile(settingsPanel.getReadDataFromFile());
            sampler.setDataFile(settingsPanel.getDataFile());
        }
    }

}

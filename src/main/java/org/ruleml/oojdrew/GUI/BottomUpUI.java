// OO jDREW - An Object Oriented extension of the Java Deductive Reasoning Engine for the Web
// Copyright (C) 2008 Ben Craig
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

package org.ruleml.oojdrew.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import org.ruleml.oojdrew.BottomUp.BottomUpApp;
import org.ruleml.oojdrew.parsing.InputFormat;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.text.NumberFormat;

public class BottomUpUI implements UI {

	private final JFrame frmOoJdrew = new JFrame();
	private final ButtonGroup typeDefinitionButtonGroup = new ButtonGroup();
	private final ButtonGroup knowledgeBaseButtonGroup = new ButtonGroup();
	private UndoRedoTextArea typeDefinitionTextArea;
	private UndoRedoTextArea knowledgeBaseTextArea;
    private UndoRedoTextArea outputTextArea;
    private JCheckBoxMenuItem chckbxmntmValidateRuleml;
    private JMenuItem mntmShowDebugConsole;
    private JPanel typeDefinitonTab;
    private JPanel knowledgeBaseTab;
    private JTabbedPane tabbedPane;
    private JRadioButton typeDefinitionFormatRDFS;
    private JRadioButton knowledgeBaseInputFormatRuleML;
    private JCheckBox chkBoxPrintRules;
    private JCheckBox chkBoxTestForStratification;
    private JCheckBox chkBoxSeparateFacts;
    private JFormattedTextField tfInputLoopCounter;
    
    // UI controller class
    private BottomUpApp controller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BottomUpUI window = new BottomUpUI();
					window.frmOoJdrew.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BottomUpUI() {
		initialize();
        updateUI();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		frmOoJdrew.setTitle("OO jDREW");
        frmOoJdrew.setBounds(100, 100, 645, 700);
		frmOoJdrew.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmOoJdrew.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpenFile = new JMenuItem("Open file...");
		mntmOpenFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mntmOpenFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.openFile();
			}
		});
		mnFile.add(mntmOpenFile);
		
		JMenuItem mntmOpenUri = new JMenuItem("Open URI...");
		mntmOpenUri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.openURI();
			}
		});
		mnFile.add(mntmOpenUri);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save as...");
		mntmSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmSaveAs.setMnemonic(KeyEvent.VK_S);
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveFileAs();
			}
		});
		mnFile.add(mntmSaveAs);
		
		mnFile.addSeparator();
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_MASK));
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnOptions = new JMenu("Options");
		menuBar.add(mnOptions);
		
		chckbxmntmValidateRuleml = new JCheckBoxMenuItem("Validate RuleML");
		chckbxmntmValidateRuleml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.applySettingsFromUI();
			}
		});
		mnOptions.add(chckbxmntmValidateRuleml);
		
		mntmShowDebugConsole = new JMenuItem("Show debug console");
		mntmShowDebugConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showDebugConsole();
			}
		});
		mnOptions.add(mntmShowDebugConsole);
		
		JMenuItem mntmPreferences = new JMenuItem("Preferences...");
		mntmPreferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
		mntmPreferences.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.showPreferenceDialog();
			}
		});
		mnOptions.add(mntmPreferences);
		frmOoJdrew.getContentPane().setLayout(new BorderLayout(0, 0));
			
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frmOoJdrew.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		typeDefinitonTab = new JPanel();
		tabbedPane.addTab("Type definition", null, typeDefinitonTab, null);
		
		JButton btnLoadTypeInformation = new JButton("Load type information");
		btnLoadTypeInformation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.parseTypeInformation();
			}
		});
		
		JLabel typeDefinitionFormatLabel = new JLabel("Input format:");
		
		typeDefinitionFormatRDFS = new JRadioButton("RDFS");
		typeDefinitionButtonGroup.add(typeDefinitionFormatRDFS);
		
		JRadioButton typeDefinitionFormatPOSL = new JRadioButton("POSL");
		typeDefinitionFormatPOSL.setSelected(true);
		typeDefinitionButtonGroup.add(typeDefinitionFormatPOSL);
		
		JScrollPane typeDefinitionScrollPane = new JScrollPane();
		GroupLayout gl_typeDefinitonTab = new GroupLayout(typeDefinitonTab);
		gl_typeDefinitonTab.setHorizontalGroup(
			gl_typeDefinitonTab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_typeDefinitonTab.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_typeDefinitonTab.createParallelGroup(Alignment.LEADING)
						.addComponent(typeDefinitionScrollPane, GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
						.addGroup(gl_typeDefinitonTab.createSequentialGroup()
							.addComponent(typeDefinitionFormatLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(typeDefinitionFormatRDFS)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(typeDefinitionFormatPOSL)
							.addPreferredGap(ComponentPlacement.RELATED, 231, Short.MAX_VALUE)
							.addComponent(btnLoadTypeInformation)))
					.addContainerGap())
		);
		gl_typeDefinitonTab.setVerticalGroup(
			gl_typeDefinitonTab.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_typeDefinitonTab.createSequentialGroup()
					.addContainerGap()
					.addComponent(typeDefinitionScrollPane, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_typeDefinitonTab.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnLoadTypeInformation)
						.addComponent(typeDefinitionFormatLabel)
						.addComponent(typeDefinitionFormatRDFS)
						.addComponent(typeDefinitionFormatPOSL))
					.addContainerGap())
		);
		
		typeDefinitionTextArea = new UndoRedoTextArea("");
		typeDefinitionScrollPane.setViewportView(typeDefinitionTextArea);
		typeDefinitonTab.setLayout(gl_typeDefinitonTab);
		
		knowledgeBaseTab = new JPanel();
		tabbedPane.addTab("Knowledge base", null, knowledgeBaseTab, null);
		
		JButton btnParseKnowledgeBase = new JButton("Parse knowledge base");
		btnParseKnowledgeBase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.parseKnowledgeBase();
			}
		});
		
		JLabel knowledgeBaseInputFormatLabel = new JLabel("Input format:");
		
		knowledgeBaseInputFormatRuleML = new JRadioButton("RuleML");
		knowledgeBaseButtonGroup.add(knowledgeBaseInputFormatRuleML);
		
		JRadioButton knowledgeBaseInputFormatPOSL = new JRadioButton("POSL");
		knowledgeBaseInputFormatPOSL.setSelected(true);
		knowledgeBaseButtonGroup.add(knowledgeBaseInputFormatPOSL);
		
		JScrollPane knowledgeBaseScrollPane = new JScrollPane();
		GroupLayout gl_knowledgeBaseTab = new GroupLayout(knowledgeBaseTab);
		gl_knowledgeBaseTab.setHorizontalGroup(
			gl_knowledgeBaseTab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_knowledgeBaseTab.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_knowledgeBaseTab.createParallelGroup(Alignment.LEADING)
						.addComponent(knowledgeBaseScrollPane, GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
						.addGroup(gl_knowledgeBaseTab.createSequentialGroup()
							.addComponent(knowledgeBaseInputFormatLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(knowledgeBaseInputFormatRuleML)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(knowledgeBaseInputFormatPOSL)
							.addPreferredGap(ComponentPlacement.RELATED, 221, Short.MAX_VALUE)
							.addComponent(btnParseKnowledgeBase)))
					.addContainerGap())
		);
		gl_knowledgeBaseTab.setVerticalGroup(
			gl_knowledgeBaseTab.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_knowledgeBaseTab.createSequentialGroup()
					.addContainerGap()
					.addComponent(knowledgeBaseScrollPane, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_knowledgeBaseTab.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnParseKnowledgeBase)
						.addComponent(knowledgeBaseInputFormatLabel)
						.addComponent(knowledgeBaseInputFormatRuleML)
						.addComponent(knowledgeBaseInputFormatPOSL))
					.addContainerGap())
		);
		
		knowledgeBaseTextArea = new UndoRedoTextArea("");

		knowledgeBaseScrollPane.setViewportView(knowledgeBaseTextArea);
		knowledgeBaseTab.setLayout(gl_knowledgeBaseTab);
		
		JPanel outputTab = new JPanel();
		tabbedPane.addTab("Output", null, outputTab, null);
		
		JScrollPane outputScrollPane = new JScrollPane();
		
		JLabel outputConfigurationLabel = new JLabel("Output:");
		
		chkBoxPrintRules = new JCheckBox("Print Rules");
		
		chkBoxSeparateFacts = new JCheckBox("Separate Facts");
		
		JButton btnRunForwardReasoner = new JButton("Run Forward Reasoner");
		btnRunForwardReasoner.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		        controller.runForwardReasoner();
		    }
		});
		
		GroupLayout gl_outputTab = new GroupLayout(outputTab);
		gl_outputTab.setHorizontalGroup(
		    gl_outputTab.createParallelGroup(Alignment.LEADING)
		        .addGroup(gl_outputTab.createSequentialGroup()
		            .addContainerGap()
		            .addGroup(gl_outputTab.createParallelGroup(Alignment.LEADING)
		                .addComponent(outputScrollPane, GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE)
		                .addGroup(gl_outputTab.createSequentialGroup()
		                    .addComponent(outputConfigurationLabel)
		                    .addPreferredGap(ComponentPlacement.UNRELATED)
		                    .addComponent(chkBoxPrintRules)
		                    .addPreferredGap(ComponentPlacement.RELATED)
		                    .addComponent(chkBoxSeparateFacts, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
		                    .addPreferredGap(ComponentPlacement.RELATED, 310, Short.MAX_VALUE)
		                    .addComponent(btnRunForwardReasoner)))
		            .addContainerGap())
		);
		gl_outputTab.setVerticalGroup(
		    gl_outputTab.createParallelGroup(Alignment.TRAILING)
		        .addGroup(gl_outputTab.createSequentialGroup()
		            .addContainerGap()
		            .addComponent(outputScrollPane, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
		            .addPreferredGap(ComponentPlacement.RELATED)
		            .addGroup(gl_outputTab.createParallelGroup(Alignment.BASELINE)
		                .addComponent(btnRunForwardReasoner)
		                .addComponent(outputConfigurationLabel)
		                .addComponent(chkBoxPrintRules)
		                .addComponent(chkBoxSeparateFacts))
		            .addContainerGap())
		);
		outputTab.setLayout(gl_outputTab);
		outputTextArea = new UndoRedoTextArea("");
		outputTextArea.setEditable(false);

        outputScrollPane.setViewportView(outputTextArea);
        
        JPanel panel = new JPanel();
        frmOoJdrew.getContentPane().add(panel, BorderLayout.SOUTH);
        
        chkBoxTestForStratification = new JCheckBox("Test For Stratification");
        
        JLabel lblConfiguration = new JLabel("Configuration:");
        
        JLabel lblNewLabel = new JLabel("Input Loop Counter:");
        
        tfInputLoopCounter = new JFormattedTextField(0);
        
        tfInputLoopCounter.setColumns(10);
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblConfiguration)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(chkBoxTestForStratification)
                    .addGap(18)
                    .addComponent(lblNewLabel)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(tfInputLoopCounter, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(353, Short.MAX_VALUE))
        );
        gl_panel.setVerticalGroup(
            gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblConfiguration)
                        .addComponent(chkBoxTestForStratification)
                        .addComponent(lblNewLabel)
                        .addComponent(tfInputLoopCounter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);
		
		tabbedPane.setSelectedIndex(1);
	}

	public boolean getFrameVisible() {
		return frmOoJdrew.isVisible();
	}
	
	public void setFrameVisible(boolean visible) {
		frmOoJdrew.setVisible(visible);
	}
	
	public void setController(AbstractUIApp controller)
	{
		this.controller = (BottomUpApp) controller;
		this.controller.syncUIWithSettings();
	}

	public void updateUI() {
		knowledgeBaseTextArea.updateUI();
		typeDefinitionTextArea.updateUI();
		outputTextArea.updateUI();
		
		SwingUtilities.updateComponentTreeUI(getFrmOoJdrew());
		getFrmOoJdrew().pack();
	}
	
	public JFrame getFrmOoJdrew() {
		return frmOoJdrew;
	}
	
	public boolean getChckbxmntmValidateRulemlSelected() {
		return chckbxmntmValidateRuleml.isSelected();
	}
	
	public void setChckbxmntmValidateRulemlSelected(boolean selected) {
		chckbxmntmValidateRuleml.setSelected(selected);
	}
	
	private EditingTab currentEditingTab()
	{
		switch(getTabbedPaneSelectedIndex())
		{
		case 0:
			return EditingTab.EditingTabTypeDefinition;
			
		case 1:
			return EditingTab.EditingTabKnowledgeBase;
			
		case 2:
			return EditingTab.EditingTabOutput;
			
		default:
			throw new RuntimeException("Unknown tab selected.");
		}
	}
	
	private void clearCurrentEditingTab()
	{
		switch(currentEditingTab())
		{
		case EditingTabTypeDefinition:
			setTypeDefinitionTextAreaText("");
			break;
			
		case EditingTabKnowledgeBase:
			setKnowledgeBaseTextAreaText("");
			break;
			
		case EditingTabOutput:
			setOutputTextAreaText("");
			break;
		}
	}
	
	public void appendToCurrentEditingTab(String content)
	{
		StringBuilder stringBuilder = new StringBuilder();
		
		switch(currentEditingTab())
		{
		case EditingTabTypeDefinition:
			stringBuilder.append(getTypeDefinitionTextAreaText());
			stringBuilder.append(content);
			setTypeDefinitionTextAreaText(stringBuilder.toString());
			break;
			
		case EditingTabKnowledgeBase:
			stringBuilder.append(getKnowledgeBaseTextAreaText());
			stringBuilder.append(content);
			setKnowledgeBaseTextAreaText(stringBuilder.toString());
			break;
			
		case EditingTabQuery:
			stringBuilder.append(getOutputTextAreaText());
			stringBuilder.append(content);
			setOutputTextAreaText(stringBuilder.toString());
			break;
		}		
	}
	
	public void setTextForCurrentEditingTab(String content)
	{
		clearCurrentEditingTab();
		appendToCurrentEditingTab(content);
	}
	
	public String getTextForCurrentEditingTab()
	{
		String text = "";
		switch(currentEditingTab())
		{
		case EditingTabTypeDefinition:
			text = getTypeDefinitionTextAreaText();
			break;
			
		case EditingTabKnowledgeBase:
			text = getKnowledgeBaseTextAreaText();
			break;
			
		case EditingTabQuery:
			text = getOutputTextAreaText();
			break;
		}
		return text;
	}
	
	public String getTypeDefinitionTextAreaText() {
		return typeDefinitionTextArea.getText();
	}
	
	private void setTypeDefinitionTextAreaText(String text) {
		typeDefinitionTextArea.setText(text);
	}
	
	public String getKnowledgeBaseTextAreaText() {
		return knowledgeBaseTextArea.getText();
	}
	
	private void setKnowledgeBaseTextAreaText(String text_1) {
		knowledgeBaseTextArea.setText(text_1);
	}
	
	public String getOutputTextAreaText() {
		return outputTextArea.getText();
	}
	
	public void setOutputTextAreaText(String text) {
	    outputTextArea.setText(text);
	}
	
	private int getTabbedPaneSelectedIndex() {
		return tabbedPane.getSelectedIndex();
	}
	
	public InputFormat getTypeInformationInputFormat()
	{
		if(getTypeDefinitionFormatRDFSSelected())
		{
			return InputFormat.InputFormatRFDS;
		}
		
		return InputFormat.InputFormatPOSL;
	}
	
	private boolean getTypeDefinitionFormatRDFSSelected() {
		return typeDefinitionFormatRDFS.isSelected();
	}
	
	private boolean getKnowledgeBaseInputFormatRuleMLSelected() {
		return knowledgeBaseInputFormatRuleML.isSelected();
	}
	
	public InputFormat getKnowledgeBaseInputFormat()
	{
		if(getKnowledgeBaseInputFormatRuleMLSelected())
		{
			return InputFormat.InputFormatRuleML;
		}
		
		return InputFormat.InputFormatPOSL;
	}

    public boolean getStratificationCheckEnabled() {
        return chkBoxTestForStratification.isSelected();
    }
    
    public boolean getSeparateFactsEnabled() {
        return chkBoxSeparateFacts.isSelected();
    }
    
    public String getInputLoopCount() {
        return tfInputLoopCounter.getValue().toString();
    }

    public boolean getPrintRulesEnabled() {
        return chkBoxPrintRules.isSelected();
    }
}

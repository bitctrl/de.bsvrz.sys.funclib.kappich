/*
 * Copyright 2017 by Kappich Systemberatung Aachen
 * 
 * This file is part of de.bsvrz.sys.funclib.kappich.
 * 
 * de.bsvrz.sys.funclib.kappich is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * de.bsvrz.sys.funclib.kappich is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with de.bsvrz.sys.funclib.kappich; If not, see <http://www.gnu.org/licenses/>.

 * Contact Information:
 * Kappich Systemberatung
 * Martin-Luther-Straße 14
 * 52062 Aachen, Germany
 * phone: +49 241 4090 436 
 * mail: <info@kappich.de>
 */

package de.bsvrz.sys.funclib.kappich.filechooser;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

import static java.awt.FileDialog.LOAD;
import static java.awt.FileDialog.SAVE;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;

/**
 * Implementiert die wesentliche Funktionalität eines JFileChooser mit
 * Hilfe der AWT FileDialog Klasse, die unter macOS die nativen
 * Dateiauswahldialoge verwenden
 *
 * @author Kappich Systemberatung, Aachen
 * @version $Revision: 0000 $
 */
public class AwtFileChooser extends JFileChooser implements FileChooser {

//	static final boolean ON_MAC = System.getProperty("os.name").toLowerCase().startsWith("mac");

	private AwtFileChooser() { }

	public static AwtFileChooser createFileChooser() {
		// Platzhalter, falls später andere Implementierungen benötigt werden
		return new AwtFileChooser();
	}

//	@Override
//	public void setCurrentDirectory(final File dir) {
//		// Ignorieren, damit Standardverhalten vom FileChooser verwendet wird
//	}

	@Override
	public int showOpenDialog() {
		if(isDirectorySelectionEnabled()) return chooseFolder();
		return chooseFiles(LOAD);
	}

	@Override
	public int showSaveDialog() {
		if(isDirectorySelectionEnabled()) return chooseFolder();
		return chooseFiles(SAVE);
	}

	private int chooseFolder() {
		// Property bewirkt unter macOS, dass Ordner selektiert werden können
		System.setProperty("apple.awt.fileDialogForDirectories", "true");
		try {
			return chooseFiles(LOAD);
		}
		finally {
			System.setProperty("apple.awt.fileDialogForDirectories", "false");
		}

	}

	private int chooseFiles(final int mode) {
		FileDialog chooser = createChooser(mode);
		try {
			transferProperties(chooser);
			chooser.setVisible(true);
			return extractSelections(chooser);
		}
		finally {
			disposeChooser(chooser);
		}
	}

	private FileDialog createChooser(final int mode) {
		return new FileDialog(JOptionPane.getRootFrame(), getDialogTitle(), mode);
	}

	private void disposeChooser(final FileDialog chooser) {
		chooser.dispose();
	}


	protected void transferProperties(final FileDialog chooser) {
		chooser.setMultipleMode(isMultiSelectionEnabled());

		File currentDirectory = getCurrentDirectory();
		if(currentDirectory == null) {
			chooser.setDirectory(null);
		}
		else {
			chooser.setDirectory(currentDirectory.getAbsolutePath());
		}
		chooser.setFilenameFilter(
				(dir, name) -> {
					FileFilter fileFilter = getFileFilter();
					return fileFilter == null || fileFilter.accept(new File(dir, name));
				}
		);
//		if(preselectedFile != null) {
//			chooser.setDirectory(preselectedFile.getParent());
//			chooser.setFile(preselectedFile.getName());
//		}


	}

	protected int extractSelections(final FileDialog chooser) {

		final String fileName = chooser.getFile();

		File[] selectedFiles = chooser.getFiles();
		if(fileName != null && (selectedFiles == null || selectedFiles.length == 0)) {
			// Kommt vermutlich nicht vor
			selectedFiles = new File[]{new File(chooser.getDirectory(), fileName)};
		}
		setSelectedFiles(selectedFiles);
		return ((selectedFiles != null) && (selectedFiles.length != 0)) ? APPROVE_OPTION : CANCEL_OPTION;
	}

}

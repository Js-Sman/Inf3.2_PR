/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aufgabe5_CommandDP.model_old;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;
import java.util.prefs.Preferences;
import javax.swing.table.AbstractTableModel;

/**
 * Modell zur Auführung der Kommandos der Adressverwaltungstabelle
 * @author Je-Sc, Ahrens
 */
public class AdressverwaltungModel extends AbstractTableModel
{
  private ArrayList<ArrayList<String>> daten;
  private ArrayList<String> adressEintraegeDaten;
  private ArrayList<String> adressEintraegeNamen;
  private UndoDataHolder undoData;
  private Preferences pref;
  
  public AdressverwaltungModel()
  {
    adressEintraegeDaten = new ArrayList<>();
    adressEintraegeNamen = new ArrayList<>();
    daten = new ArrayList<>();
    adressEintraegeNamen.add("Name");
    adressEintraegeDaten.add("Lehner");
    adressEintraegeNamen.add("Telefon");
    adressEintraegeDaten.add("122345");
    daten.add(adressEintraegeDaten);
    undoData = new UndoDataHolder();
  }

  @Override
  public int getRowCount()
  {
    return daten.size();
  }

  @Override
  public int getColumnCount()
  {
    return adressEintraegeDaten.size();
  }

  @Override
  public Object getValueAt(int row, int col)
  {
    return daten.get(row).get(col);
  }
  @Override
  public void setValueAt(Object value, int row, int col)
  {
    daten.get(row).set(col, (String)value);
  }
  
  @Override
  public boolean isCellEditable(int row, int col)
  {
    return true;
  }
  
  @Override
  public String getColumnName(int col)
  {
    return adressEintraegeNamen.get(col);
  }
  
  public ArrayList<String> getRowData(int row)
  {
    return daten.get(row);
  }
  
  /**
   * Fügt einen Datensatz in eine bestimmte Zeile ein
   * @param row referenzierte Zeile
   * @param rowData einzufügender Datensatz
   */
  public void insertRowData(int row, ArrayList<String> rowData)
  {
    daten.add(row, rowData);
    this.fireTableDataChanged();
  }
  
  /**
   * Löscht die Daten aus einer bestimmten Zeile
   * @param row referenzierte Zeile
   */
  public void deleteRowData(int row)
  {
    daten.remove(row);
    this.fireTableDataChanged();
  }

  /**
   * Hängt einen leeren Adresseintrag ans Ende der Tabelle
   */
  public void leerenAdressEintragAnhaengen()
  {
    adressEintraegeDaten = new ArrayList<>();
    adressEintraegeNamen.forEach(s -> adressEintraegeDaten.add(s));
    daten.add(adressEintraegeDaten);
    this.fireTableDataChanged();
  }
  
  
  public void spalteHinzufuegen(int col, String name)
  {
    adressEintraegeNamen.add(name);
    daten.forEach(s -> s.add(col, " "));
    this.fireTableStructureChanged();
  }
  
  public void spalteLoeschen(int col)
  {
    adressEintraegeNamen.remove(col);
    daten.forEach(s -> s.remove(col));
    this.fireTableStructureChanged();
  }
  
  /**
   * Versucht die Datei zu speichern
   * @param datei referenzierte Datei
   * @throws FileNotFoundException
   * @throws IOException 
   */
  public void datenSpeichern(File datei) throws FileNotFoundException, IOException
  {
    FileOutputStream fos = new FileOutputStream(datei);
    BufferedOutputStream bos = new BufferedOutputStream(fos);
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    oos.writeObject(daten);
    oos.writeObject(adressEintraegeNamen);
    oos.flush();
    oos.close();
  }
  
  /**
   * Versucht eine Datei zu öffnen
   * @param datei referenzierte Datei
   * @throws FileNotFoundException
   * @throws IOException
   * @throws ClassNotFoundException 
   */
  public void datenLesen(File datei) throws FileNotFoundException, IOException, ClassNotFoundException
  {
    FileInputStream fis = new FileInputStream(datei);
    BufferedInputStream bis = new BufferedInputStream(fis);
    ObjectInputStream ois = new ObjectInputStream(bis);
    daten = (ArrayList<ArrayList<String>>)ois.readObject();
    adressEintraegeNamen = (ArrayList<String>)ois.readObject();
    adressEintraegeDaten = daten.get(daten.size()-1);
    ois.close();
    this.fireTableDataChanged();
  }

  /**
   * Bestimmt die Adresse des zuletzt besuchten Ordners
   * @return letzter Ordner
   */
  public String getPref()
  {
        pref = Preferences.userNodeForPackage(getClass());
        return pref.get("lastDirectory", null);
  }

  /**
   * Setzt die Preferenz zu dem zuletzt besuchten Ordner
   * @param lastDirectory Bezeichner "lastDirectory"
   * @param lastAdress Adresse des zuletzt besuchten Ordners
   */
  public void putPref(String lastDirectory, String lastAdress)
  {
    pref.put(lastDirectory, lastAdress);
  }
  
  /**
   * Legt die Nummer der hinzugefügten Zeile auf den Stack von hinzu-
   * gefügten Zeilen 
   * @param row hinzugefügte Zeile
   */
  public void setAddedRow(int row){
    undoData.setAddedRow(row);
  }

  public int getLastAddedRow(){
    return undoData.getLastAddedRow();
  }

  public boolean addedStackIsEmpty(){
    return undoData.addedStackIsEmpty();
  }

   public void setDeleteRow(int row){
    undoData.setDeleteRow(row);
  }

  public int getLastDeletedRow(){
    return undoData.getLastDeletedRow();
  }

  public ArrayList<String> getLastDeletedData(){
    return undoData.getLastDeletedData();
  }

  public boolean deleteStackIsEmpty(){
    return undoData.deleteStackIsEmpty();
  }

  public void pushData(ArrayList<String> data){
    undoData.pushData(data);
  }

  public void deleteStacks(){
    undoData.deleteStacks();
  }

}

/**
 * hält Undo-Daten der Commands
 * @author Je-Sc, Ahrens
 */
class UndoDataHolder
{
  /** Stack = LIFO = Last In First Out
   *  Queue = FIFO = First In First Out
   */
  private Stack<Integer> addedRows;
  private Stack<Integer> deletedRows;
  private ArrayDeque<ArrayList<String>> stackFuerGeloeschteDatensaetze;
  // etc.
  
  public UndoDataHolder()
  {
    stackFuerGeloeschteDatensaetze = new ArrayDeque<>();
    addedRows = new Stack<Integer>();
    deletedRows = new Stack<Integer>();
  }

  public void setAddedRow(int addedRow){
    this.addedRows.push(addedRow);
  }

  public int getLastAddedRow() {
    return this.addedRows.pop();
  }

  public boolean deleteStackIsEmpty(){
    return deletedRows.isEmpty();
  }

  public void pushData(ArrayList<String> data){
    stackFuerGeloeschteDatensaetze.push(data);
  }

  public ArrayList<String> getLastDeletedData(){
    return this.stackFuerGeloeschteDatensaetze.pop();
  }

  public void setDeleteRow(int deletedRow) {
    this.deletedRows.push(deletedRow);
  }

  public int getLastDeletedRow() {
    return this.deletedRows.pop();
  }

  public boolean addedStackIsEmpty() {
    return addedRows.isEmpty();
  }

  /**
   * Löscht bei Öffnen einer neuen Datei alle Stacks
   */
  public void deleteStacks()
  {
    addedRows.clear();
    deletedRows.clear();
    stackFuerGeloeschteDatensaetze.clear();
  }

}


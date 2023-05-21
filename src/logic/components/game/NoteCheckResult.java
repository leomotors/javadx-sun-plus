package logic.components.game;

/**
 * <ul>
 * <li>NONE - Do nothing</li>
 * 
 * <li>PRESERVE - Save judgement and preserve note (Only for HOLD notes)</li>
 * 
 * <li>REMOVE - Save judgement and remove note</li>
 * </ul>
 */
public enum NoteCheckResult {
    NONE, PRESERVE, REMOVE
}

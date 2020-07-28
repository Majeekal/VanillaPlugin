package com.sbezboro.standardplugin;

import java.util.List;
import java.util.Map;

import com.sbezboro.standardplugin.commands.ICommand;
import com.sbezboro.standardplugin.model.StandardPlayer;
import com.sbezboro.standardplugin.persistence.PersistedPropertyDefinition;
import org.bukkit.plugin.Plugin;


public interface SubPlugin extends Plugin {
	public String getSubPluginName();
	public List<ICommand> getCommands();
	public void reloadPlugin();
	public List<PersistedPropertyDefinition> extraPlayerPropertyDefinitions();
}

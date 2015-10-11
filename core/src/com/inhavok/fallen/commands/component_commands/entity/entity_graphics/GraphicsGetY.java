package com.inhavok.fallen.commands.component_commands.entity.entity_graphics;

import com.inhavok.fallen.commands.DataRequest;
import com.inhavok.fallen.components.entity_components.EntityGraphics;

public class GraphicsGetY extends DataRequest<EntityGraphics> {
	private float y;
	public GraphicsGetY() {
		super(EntityGraphics.class);
	}
	@Override
	public Enum getMessage() {
		return EntityGraphics.Message.GET_Y;
	}
	@Override
	public <S> S getData(Class<S> dataClass) {
		return dataClass.cast(y);
	}
	@Override
	public <S> void setData(S data) {
		y = (Float) data;
	}
}
package com.github.cg.example.jsf.component;

import java.util.ArrayList;
import java.util.List;

import br.com.atos.utils.StringUtils;

import com.github.cg.annotation.Component;
import com.github.cg.model.Attribute;
import com.github.cg.model.AttributeFormType;
import com.github.cg.model.AttributeId;
import com.github.cg.model.AttributeOneToMany;
import com.github.cg.model.Entity;

@Component
public class EditXhtmlComponent extends BaseComponent {

	public String render(int tabs) {
		
		StringBuilder sb = new StringBuilder();
		
		Entity entity = getTargetContext().getEntity();		
		String path = entity.getNameFlc() + "EditCtrl.entity";
		String tab = tab(tabs);
		
		println(sb, "{0}<p:tabView id=\"tbView\">", tab);	
		
		renderTabEntity(sb, path, tab);
		
		for (AttributeOneToMany attribute : getTargetContext().getEntity().getAttributesOneToMany()) {
			
			if (attribute.isRenderForm()) {						
			
				String dataTableId = "dt" + attribute.getNameFuc();
				
				println(sb, "{0}\t<p:tab title=\"{0}\">", attribute.getLabel());
				
				println(sb, "{0}\t\t<p:dataTable", tab);
				println(sb, "{0}\t\t\tid=\"{1}\"", tab, dataTableId);
				println(sb, "{0}\t\t\temptyMessage=\"None {1} found.\"", tab, getTargetContext().getEntity().getLabel());
				println(sb, "{0}\t\t\tvalue=\"#'{'{1}.{2}'}'\"", tab, path, attribute.getName());
				println(sb, "{0}\t\t\tvar=\"association\">", tab);
				println(sb, "{0}\t\t\t", tab);
				
				if (AttributeFormType.EXTERNAL.equals(attribute.getFormType())) {
										
					String assocPath = "association";
					
					for (Attribute assocAttribute : attribute.getAssociationAttributesWithoutAttributeMappedByAndAttributesOneToMany()) {
						
						if (assocAttribute.isRenderColumn()) {
							println(sb, "{0}\t\t\t<p:column headerText=\"{1}\" sortBy=\"#'{'{2}.{3}'}'\" filterBy=\"#'{'{2}.{3}'}'\">", tab, assocAttribute.getLabel(), assocPath, getValue(assocAttribute));		
							printot(sb, tab + "\t\t\t\t", assocPath, assocAttribute);
							println(sb, "{0}\t\t\t</p:column>", tab);
							println(sb, "{0}\t\t\t", tab);
						}
					}

					println(sb, "{0}\t\t\t<p:column headerText=\"Action\" styleClass=\"col-action\">", tab);
					println(sb, "{0}\t\t\t\t<p:commandLink action=\"#'{'cc.attrs.winFrm.association{1}.startUpdate'}'\" title=\"Edit {2}\" process=\"@this\">", tab, attribute.getNameFuc(), attribute.getLabel());
					println(sb, "{0}\t\t\t\t\t<h:graphicImage value=\"/resources/img/s.gif\" styleClass=\"link-icone ui-icon-pencil\" />", tab);
					println(sb, "{0}\t\t\t\t\t<f:setPropertyActionListener target=\"#'{'cc.attrs.winFrm.association{1}.winFrmAssociation.entity}\" value=\"#'{'association'}'\" />", tab, attribute.getNameFuc());
					println(sb, "{0}\t\t\t\t</p:commandLink>", tab);
					println(sb, "{0}\t\t\t\t", tab);
					println(sb, "{0}\t\t\t\t<p:commandLink action=\"#'{'cc.attrs.winFrm.association{1}.startDelete'}'\" title=\"Delete {2}\" process=\"@this\">", tab, attribute.getNameFuc(), attribute.getLabel());
					println(sb, "{0}\t\t\t\t\t<h:graphicImage value=\"/resources/img/s.gif\" styleClass=\"link-icone ui-icon-trash\" />", tab);
					println(sb, "{0}\t\t\t\t\t<f:setPropertyActionListener target=\"#'{'cc.attrs.winFrm.association{1}.winFrmAssociation.entity}\" value=\"#'{'association'}'\" />", tab, attribute.getNameFuc());
					println(sb, "{0}\t\t\t\t</p:commandLink>", tab);
					println(sb, "{0}\t\t\t</p:column>", tab);
					println(sb, "{0}\t\t\t", tab);
					println(sb, "{0}\t\t\t<f:facet name=\"footer\">", tab);
					println(sb, "{0}\t\t\t\t<p:outputPanel styleClass=\"datatable-menu\" layout=\"block\">", tab);
					println(sb, "{0}\t\t\t\t\t<p:commandButton value=\"Add\" action=\"#'{'cc.attrs.winFrm.association{1}.startInsert'}'\" title=\"Add {2}\" icon=\"ui-icon-document\" process=\"@this\" />", tab, attribute.getNameFuc(), attribute.getLabel());
					println(sb, "{0}\t\t\t\t</p:outputPanel>", tab);
					println(sb, "{0}\t\t\t</f:facet>", tab);
					println(sb, "{0}\t\t</p:dataTable>", tab);
				}
				else {
					
					String assocPath = "association";
					
					AttributeId assocAttributeId = attribute.getAssociationEntity().getAttributeId();
					
					println(sb, "{0}t\t\t\t<p:column headerText=\"{0}\" sortBy=\"#'{'{1}.{2}'}'\" filterBy=\"#'{'{1}.{2}'}'\" filterStyle=\"width: 50px\" styleClass=\"width: 50px\">", assocAttributeId.getLabel(), assocPath, getValue(assocAttributeId));
					println(sb, "{0}t\t\t\t\t<h:outputText value=\"#'{'{0}.{1}'}'\" />", assocPath, assocAttributeId.getName());
					println(sb, "{0}t\t\t\t</p:column>");
					println(sb, "{0}t\t\t\t");
					
					for (Attribute assocAttribute : attribute.getAssociationAttributesWithoutAttributeMappedByAndAttributesOneToMany()) {
						
						if (assocAttribute.isRenderColumn() && !(assocAttribute instanceof AttributeId)) {
							
							println(sb, "{0}t\t\t\t<p:column headerText=\"{0}\" sortBy=\"#'{'{1}.{2}'}'\" filterBy=\"#'{'{1}.{2}'}'\">", assocAttribute.getLabel(), assocPath, getValue(assocAttribute));
							println(sb, "{0}t\t\t\t\t<h:panelGrid columns=\"2\" styleClass=\"semborda\">");
							printin(sb, "{0}t\t\t\t\t\t", assocPath, assocAttribute, false);
							println(sb, "{0}t\t\t\t\t\t<p:message for=\"{0}\" display=\"icon\" />", assocAttribute.getName());
							println(sb, "{0}t\t\t\t\t</h:panelGrid>");
							println(sb, "{0}t\t\t\t</p:column>");
							println(sb, "{0}t\t\t\t");
						}
					}
					
					println(sb, "{0}t\t\t\t<p:column headerText=\"Ação\" styleClass=\"col-acao\">");
					println(sb, "{0}t\t\t\t\t<p:commandLink action=\"#'{'cc.attrs.winFrm.association{0}.startUpdate'}'\" title=\"Excluir o objeto\" process=\"@this\">", StringUtils.firstToUpperCase(attribute.getName()));
					println(sb, "{0}t\t\t\t\t\t<h:graphicImage value=\"/resources/img/s.gif\" styleClass=\"link-icone ui-icon-trash\" />");
					println(sb, "{0}t\t\t\t\t\t<f:setPropertyActionListener target=\"#'{'cc.attrs.winFrm.association{0}.association}\" value=\"#'{'association'}'\" />", StringUtils.firstToUpperCase(attribute.getName()));
					println(sb, "{0}t\t\t\t\t</p:commandLink>");
					println(sb, "{0}t\t\t\t</p:column>");
					println(sb, "{0}t\t\t\t");
					println(sb, "{0}t\t\t\t<f:facet name=\"footer\">");
					println(sb, "{0}t\t\t\t\t<p:outputPanel styleClass=\"datatable-menu\" layout=\"block\">");
					println(sb, "{0}t\t\t\t\t\t<p:commandButton value=\"Adicionar\" action=\"#'{'cc.attrs.winFrm.association{0}.startInsert'}'\" title=\"Adiconar um ojbeto\" icon=\"ui-icon-document\" process=\"{1}\" update=\"{1}\" />", StringUtils.firstToUpperCase(attribute.getName()), dataTableId);
					println(sb, "{0}t\t\t\t\t</p:outputPanel>");
					println(sb, "{0}t\t\t\t</f:facet>");
					println(sb, "{0}t\t\t</p:dataTable>");
					println(sb, "{0}t\t\t");
					println(sb, "{0}t\t\t<atos:winFrmAssociationDelete");
					println(sb, "{0}\t\t\tfrmAssociation=\"#'{'cc.attrs.winFrm.association{0}'}'\"", StringUtils.firstToUpperCase(attribute.getName()));
					println(sb, "{0}\t\t\ttitle=\"Exclusão de {0}.\"", attribute.getLabel());
					println(sb, "{0}\t\t\tmessage=\"Você tem certeza que deseja excluir {0}?\" />", attribute.getLabel());
				}
		
				println(sb, "\t\t\t\t</p:tab>");
			}
		}

		print(sb, "\t\t\t</p:tabView>");
		
		return sb.toString();
	}

	private void renderTabEntity(StringBuilder sb, String path, String tab) {
		
		tab += "\t";
		
		println(sb, "{0}<p:tab title=\"{1}\">", tab, getTargetContext().getEntity().getLabel());
		println(sb, "{0}\t<h:panelGrid columns=\"3\" cellpadding=\"5\" style=\"width: 100%\">", tab);
		println(sb, "{0}\t\t<h:outputLabel value=\"Id:\" for=\"id\" />", tab);
		println(sb, "{0}\t\t<p:inputText id=\"id\" label=\"Id.\" value=\"#'{'{1}.id'}'\" disabled=\"true\" />", tab, path);
		println(sb, "{0}\t\t<p:message for=\"id\" display=\"icon\" />", tab);
		
		List<Attribute> attributes = new ArrayList<Attribute>();
		
		for (Attribute attribute : getTargetContext().getEntity().getAttributes()) {
			
			if (attribute.isRenderForm() && !AttributeId.class.isInstance(attribute) && !AttributeOneToMany.class.isInstance(attribute)) {
					
				// Verifica se o atributo e anotado com @Version
				if (attribute.isAnnotedWithVersion()) {
					println(sb, "{0}\t\t", tab);
					println(sb, "{0}\t\t<h:outputLabel value=\"{1}:\" for=\"{1}\" />", tab, attribute.getLabel(), attribute.getName());
					println(sb, "{0}\t\t<p:inputText id=\"{1}\" label=\"{2}\" value=\"#'{'{3}.{1}'}'\" disabled=\"true\" />", tab, attribute.getName(), attribute.getLabel(), path);
					println(sb, "{0}\t\t<p:message for=\"{1}\" display=\"icon\" />", tab, attribute.getName());
				}
				else {
					attributes.add(attribute);
				}
			}
		}

		for (Attribute attribute : attributes) {
			println(sb, "{0}\t\t", tab);
			println(sb, "{0}\t\t<h:outputLabel value=\"{1}:\" for=\"{2}\" />", tab, attribute.getLabel(), attribute.getName());			
			printin(sb, "{0}\t\t", path, attribute, true);
			println(sb, "{0}\t\t<p:message for=\"{1}\" display=\"icon\" />", tab, attribute.getName());
		}

		println(sb, "{0}\t</h:panelGrid>", tab);
		println(sb, "{0}</p:tab>", tab);
	}
}
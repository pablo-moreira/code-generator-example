package com.github.cg.example.jsf.component;

import br.com.atos.utils.StringUtils;

import com.github.cg.annotation.Component;
import com.github.cg.model.Attribute;
import com.github.cg.model.AttributeOneToMany;

@Component
public class ViewXhtmlComponent extends BaseComponent {

	public String render() {

		StringBuilder sb = new StringBuilder();
		
		String ctrl = getTargetContext().getEntity().getNameFlc() + "ViewCtrl.";
		String path = ctrl + "entity";
		
		println(sb, "\t\t\t\t\t<p:tab title=\"{0}\">", getTargetContext().getEntity().getLabel());		
		println(sb, "\t\t\t\t\t\t<h:panelGrid columns=\"2\" cellpadding=\"5\">");
		
		for (Attribute attribute : getTargetContext().getEntity().getAttributes()) {
			
			// Ignora as associacoes OneToMany
			if (!AttributeOneToMany.class.isInstance(attribute)) {
		
				println(sb, "\t\t\t\t\t\t");
				println(sb, "\t\t\t\t\t\t\t<h:outputText value=\"{0}\" />", attribute.getLabel());
				printot(sb, "\t\t\t\t\t\t\t", path, attribute);
			}
		}

		println(sb, "\t\t\t\t\t\t</h:panelGrid>");		
		println(sb, "\t\t\t\t\t</p:tab>");
		
		for (AttributeOneToMany attribute : getTargetContext().getEntity().getAttributesOneToMany()) {
			
			println(sb, "\t\t\t\t\t<p:tab title=\"{0}\">", attribute.getLabel());
			
			println(sb, "\t\t\t\t\t\t<p:dataTable");
			println(sb, "\t\t\t\t\t\t\temptyMessage=\"Nenhum objeto cadastrado.\"");
			println(sb, "\t\t\t\t\t\t\tvalue=\"#'{'{0}{1}'}'\"", path + ".", attribute.getName());
			println(sb, "\t\t\t\t\t\t\tvar=\"associacao\">");
			println(sb, "\t\t\t\t\t\t\t");			

			String assocPath = "associacao";
			
			for (Attribute assocAttribute : attribute.getAssociationAttributesWithoutAttributeMappedByAndAttributesOneToMany()) {
				
				if (assocAttribute.isRenderColumn()) {				
					println(sb, "\t\t\t\t\t\t\t<p:column headerText=\"{0}\">", assocAttribute.getLabel());				
					printot(sb, "\t\t\t\t\t\t\t\t", assocPath, assocAttribute);
					println(sb, "\t\t\t\t\t\t\t</p:column>");
					println(sb, "\t\t\t\t\t\t\t");
				}
			}
		
			println(sb, "\t\t\t\t\t\t\t<p:column headerText=\"Ação\" styleClass=\"col-acao\">");
			println(sb, "\t\t\t\t\t\t\t\t<h:link outcome=\"/pages/{0}/{0}Visualizar.jsf\" title=\"Visualizar o objeto\">", StringUtils.firstToLowerCase(attribute.getAssociationClassSimpleName()));
			println(sb, "\t\t\t\t\t\t\t\t\t<f:param name=\"id\" value=\"#{associacao.id}\" />");
			println(sb, "\t\t\t\t\t\t\t\t\t<f:param name=\"revisaoId\" value=\"#'{'{0}'}'\" rendered=\"#'{'{1}'}'\" />", ctrl + "revisaoId", ctrl + "revisaoId != null");
			println(sb, "\t\t\t\t\t\t\t\t\t<h:graphicImage value=\"/resources/img/s.gif\" styleClass=\"link-icone ui-icon-zoomin\" />");
			println(sb, "\t\t\t\t\t\t\t\t</h:link>");
			println(sb, "\t\t\t\t\t\t\t</p:column>");			
			
			println(sb, "\t\t\t\t\t\t</p:dataTable>");
			println(sb, "\t\t\t\t\t</p:tab>");
			
			
		}
		return sb.toString();
	}
}